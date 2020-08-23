package com.bht.saigonparking.service.auth.service.impl;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.MAIL_TOPIC_ROUTING_KEY;
import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.USER_TOPIC_ROUTING_KEY;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.mail.MailRequest;
import com.bht.saigonparking.api.grpc.mail.MailRequestType;
import com.bht.saigonparking.api.grpc.user.UpdateUserLastSignInRequest;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.auth.entity.UserTokenEntity;
import com.bht.saigonparking.service.auth.repository.UserTokenRepository;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Async
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceHelperImpl {

    private final UserTokenRepository userTokenRepository;
    private final RabbitTemplate rabbitTemplate;
    private final UserServiceGrpc.UserServiceStub userServiceStub;

    public void updateUserLastSignIn(@NotNull Long userId) {
        rabbitTemplate.convertAndSend(USER_TOPIC_ROUTING_KEY, UpdateUserLastSignInRequest.newBuilder()
                .setUserId(userId)
                .setTimeInMillis(System.currentTimeMillis())
                .build());
    }

    public void activateUserWithId(@NotNull Long userId) {
        userServiceStub.activateUser(Int64Value.of(userId), new StreamObserver<Empty>() {
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
    }

    public void sendMail(@NotNull MailRequestType type,
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

    public void saveUserRefreshToken(@NotNull Long userId, @NotEmpty UUID tokenId) {
        try {
            /* update refresh token id to database if already existed */
            UserTokenEntity userTokenEntity = userTokenRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
            userTokenEntity.setTokenId(tokenId);
            userTokenRepository.saveAndFlush(userTokenEntity);

        } catch (EntityNotFoundException entityNotFoundException) {
            /* save new refresh token id to database if not existed before */
            UserTokenEntity userTokenEntity = UserTokenEntity.builder().userId(userId).tokenId(tokenId).build();
            userTokenRepository.saveAndFlush(userTokenEntity);

        } finally {
            LoggingUtil.log(Level.INFO, "SERVICE", "Success", String.format("saveUserRefreshToken(%d, %s)", userId, tokenId));
        }
    }
}