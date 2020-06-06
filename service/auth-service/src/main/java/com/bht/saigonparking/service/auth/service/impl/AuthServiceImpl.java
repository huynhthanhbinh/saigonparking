package com.bht.saigonparking.service.auth.service.impl;

import static com.bht.saigonparking.api.grpc.mail.MailRequestType.ACTIVATE_ACCOUNT;
import static com.bht.saigonparking.api.grpc.mail.MailRequestType.RESET_PASSWORD;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.api.grpc.auth.RegisterRequest;
import com.bht.saigonparking.api.grpc.auth.ValidateResponseType;
import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.service.auth.service.AuthService;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

import io.grpc.Context;
import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {

    private final SaigonParkingAuthentication authentication;
    private final AuthServiceImplHelper authServiceImplHelper;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public Triple<ValidateResponseType, String, String> validateLogin(@NotEmpty String username,
                                                                      @NotEmpty String password,
                                                                      @NotNull UserRole userRole) {

        User user = userServiceBlockingStub.getUserByUsername(StringValue.of(username));
        if (user.getRole().equals(userRole)) {
            if (Boolean.TRUE.equals(user.getIsActivated())) {
                if (password.equals(user.getPassword())) {

                    /* Asynchronously update user last sign in */
                    Context context = Context.current().fork();
                    context.run(() -> authServiceImplHelper.updateUserLastSignIn(user.getId()));

                    /* Generate new access token, new refresh token for user with Id, Role */
                    Pair<String, String> generatedAccessToken = authentication.generateAccessToken(user.getId(), user.getRole().toString());
                    Pair<String, String> generatedRefreshToken = authentication.generateRefreshToken(user.getId(), user.getRole().toString());

                    /* Asynchronously save user token to the database */
                    authServiceImplHelper.saveUserRefreshToken(user.getId(), generatedRefreshToken.getFirst());

                    /* Return response with two field: 1st ResponseType, 2nd AccessToken */
                    return Triple.of(ValidateResponseType.AUTHENTICATED, generatedAccessToken.getSecond(), generatedRefreshToken.getSecond());
                }
                return Triple.of(ValidateResponseType.INCORRECT, "", "");
            }
            return Triple.of(ValidateResponseType.INACTIVATED, "", "");
        }
        return Triple.of(ValidateResponseType.DISALLOWED, "", "");
    }

    @Override
    public String registerUser(@NotNull RegisterRequest request) {
        UserRole userRole = UserRole.CUSTOMER;
        Long userId = userServiceBlockingStub.createCustomer(Customer.newBuilder()
                .setUserInfo(User.newBuilder()
                        .setUsername(request.getUsername())
                        .setPassword(request.getPassword())
                        .setEmail(request.getEmail())
                        .setRole(userRole)
                        .build())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setPhone(request.getPhone())
                .build())
                .getValue();

        String activateAccountToken = authentication.generateActivateAccountToken(userId, userRole.toString()).getSecond();
        authServiceImplHelper.sendMail(ACTIVATE_ACCOUNT, request.getEmail(), request.getUsername(), activateAccountToken);

        return request.getEmail();
    }

    @Override
    public String sendResetPasswordEmail(@NotEmpty String username) {
        User user = userServiceBlockingStub.getUserByUsername(StringValue.of(username));
        String resetPasswordToken = authentication.generateResetPasswordToken(user.getId(), user.getRole().toString()).getSecond();

        authServiceImplHelper.sendMail(RESET_PASSWORD, user.getEmail(), username, resetPasswordToken);
        return user.getEmail();
    }

    @Override
    public String sendActivateAccountEmail(@NotEmpty String username) {
        User user = userServiceBlockingStub.getUserByUsername(StringValue.of(username));
        String activateAccountToken = authentication.generateActivateAccountToken(user.getId(), user.getRole().toString()).getSecond();

        authServiceImplHelper.sendMail(ACTIVATE_ACCOUNT, user.getEmail(), username, activateAccountToken);
        return user.getEmail();
    }

    @Override
    public Triple<String, String, String> generateNewToken(@NotNull Long userId,
                                                           @NotNull Date currentExp,
                                                           @NotEmpty String currentTokenId,
                                                           boolean currentIsRefreshToken) {

        User user = userServiceBlockingStub.getUserById(Int64Value.of(userId));

        return authServiceImplHelper.generateNewToken(user, currentExp, currentTokenId, currentIsRefreshToken);
    }

    @Override
    public Triple<String, String, String> activateNewAccount(@NotNull Long userId,
                                                             @NotNull Date currentExp,
                                                             @NotEmpty String currentTokenId,
                                                             boolean currentIsRefreshToken) {

        User user = userServiceBlockingStub.getUserById(Int64Value.of(userId));

        /* Asynchronously activate user */
        Context context = Context.current().fork();
        context.run(() -> authServiceImplHelper.activateUserWithId(userId));

        return authServiceImplHelper.generateNewToken(user, currentExp, currentTokenId, currentIsRefreshToken);
    }
}