package com.bht.saigonparking.service.auth.service.impl;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.MAIL_TOPIC_ROUTING_KEY;
import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.USER_TOPIC_ROUTING_KEY;

import java.util.Date;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Level;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.mail.MailRequest;
import com.bht.saigonparking.api.grpc.mail.MailRequestType;
import com.bht.saigonparking.api.grpc.user.UpdateUserLastSignInRequest;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.exception.InvalidRefreshTokenException;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.auth.entity.UserTokenEntity;
import com.bht.saigonparking.service.auth.repository.UserTokenRepository;
import com.bht.saigonparking.service.auth.service.ConnectionService;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImplHelper {

    private final ConnectionService connectionService;
    private final SaigonParkingAuthentication authentication;
    private final UserTokenRepository userTokenRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${service.user.id}")
    private String userServiceId;

    void updateUserLastSignIn(@NotNull Long userId) {
        rabbitTemplate.convertAndSend(USER_TOPIC_ROUTING_KEY, UpdateUserLastSignInRequest.newBuilder()
                .setUserId(userId)
                .setTimeInMillis(System.currentTimeMillis())
                .build());
    }

    void activateUserWithId(@NotNull Long userId) {
        ManagedChannel channel = connectionService.createChannelOfService(userServiceId);

        connectionService.getUserServiceStub(channel).activateUser(Int64Value.of(userId), new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {
                LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                        String.format("activateUserWithId(%d)", userId));
            }

            @Override
            public void onError(Throwable e) {
                LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", e.getMessage());
            }

            @Override
            public void onCompleted() {
                // finish request
            }
        });

        channel.shutdown();
    }

    void sendMail(@NotNull MailRequestType type,
                  @NotEmpty String email,
                  @NotEmpty String username,
                  @NotEmpty String temporaryToken) {

        rabbitTemplate.convertAndSend(MAIL_TOPIC_ROUTING_KEY, MailRequest.newBuilder()
                .setType(type)
                .setEmail(email)
                .setUsername(username)
                .setTemporaryToken(temporaryToken)
                .build());
    }

    @Async
    public void saveUserRefreshToken(@NotNull Long userId, @NotEmpty String tokenId) {
        try {
            UserTokenEntity userTokenEntity = userTokenRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
            userTokenEntity.setTokenId(tokenId);
            userTokenRepository.saveAndFlush(userTokenEntity);

        } catch (EntityNotFoundException entityNotFoundException) {

            UserTokenEntity userTokenEntity = UserTokenEntity.builder().userId(userId).tokenId(tokenId).build();
            userTokenRepository.saveAndFlush(userTokenEntity);

        } finally {
            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("saveUserRefreshToken(%d, %s)", userId, tokenId));
        }
    }

    Triple<String, String, String> generateNewToken(@NotNull User user,
                                                    @NotNull Date currentExp,
                                                    @NotEmpty String currentTokenId,
                                                    boolean currentIsRefreshToken) {

        try {
            if (currentIsRefreshToken) {
                userTokenRepository.findByTokenId(currentTokenId).orElseThrow(EntityNotFoundException::new);
                userTokenRepository.flush();
            }
        } catch (EntityNotFoundException entityNotFoundException) {
            throw new InvalidRefreshTokenException();
        }

        Pair<String, String> generatedAccessToken = authentication.generateAccessToken(user.getId(), user.getRole().toString());

        if (((currentExp.getTime() - new Date().getTime()) / 86400000) > 7) { /* Token not nearly expire */
            return Triple.of(user.getUsername(), generatedAccessToken.getSecond(), "");

        } else { /* Token nearly expire */

            /* Generate new refresh token for user with Id, Role */
            Pair<String, String> generatedRefreshToken = authentication.generateRefreshToken(user.getId(), user.getRole().toString());
            saveUserRefreshToken(user.getId(), generatedRefreshToken.getFirst());

            return Triple.of(user.getUsername(), generatedAccessToken.getSecond(), generatedRefreshToken.getSecond());
        }
    }
}