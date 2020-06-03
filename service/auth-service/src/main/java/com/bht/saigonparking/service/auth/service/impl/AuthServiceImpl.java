package com.bht.saigonparking.service.auth.service.impl;

import static com.bht.saigonparking.api.grpc.mail.MailRequestType.ACTIVATE_ACCOUNT;
import static com.bht.saigonparking.api.grpc.mail.MailRequestType.RESET_PASSWORD;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
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
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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
                    String accessToken = authentication.generateAccessToken(user.getId(), userRole.toString());
                    String refreshToken = authentication.generateRefreshToken(user.getId(), userRole.toString());

                    /* Return response with two field: 1st ResponseType, 2nd AccessToken */
                    return Triple.of(ValidateResponseType.AUTHENTICATED, accessToken, refreshToken);
                }
                return Triple.of(ValidateResponseType.INCORRECT, "", "");
            }
            return Triple.of(ValidateResponseType.INACTIVATED, "", "");
        }
        return Triple.of(ValidateResponseType.DISALLOWED, "", "");
    }

    @Override
    public String registerUser(RegisterRequest request) {
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

        String activateAccountToken = authentication.generateActivateAccountToken(userId, userRole.toString());
        authServiceImplHelper.sendMail(ACTIVATE_ACCOUNT, request.getEmail(), request.getUsername(), activateAccountToken);

        return request.getEmail();
    }

    @Override
    public String sendResetPasswordEmail(String username) {
        User user = userServiceBlockingStub.getUserByUsername(StringValue.of(username));
        if (user.equals(User.getDefaultInstance())) {
            throw new StatusRuntimeException(Status.UNKNOWN);
        }

        String resetPasswordToken = authentication.generateResetPasswordToken(user.getId(), user.getRole().toString());
        authServiceImplHelper.sendMail(RESET_PASSWORD, user.getEmail(), username, resetPasswordToken);
        return user.getEmail();
    }

    @Override
    public String sendActivateAccountEmail(String username) {
        User user = userServiceBlockingStub.getUserByUsername(StringValue.of(username));
        if (user.equals(User.getDefaultInstance())) {
            throw new StatusRuntimeException(Status.UNKNOWN);
        }

        String activateAccountToken = authentication.generateActivateAccountToken(user.getId(), user.getRole().toString());
        authServiceImplHelper.sendMail(ACTIVATE_ACCOUNT, user.getEmail(), username, activateAccountToken);
        return user.getEmail();
    }

    @Override
    public Triple<String, String, String> generateNewToken(Long userId) {
        User user = userServiceBlockingStub.getUserById(Int64Value.of(userId));
        if (user.equals(User.getDefaultInstance())) {
            throw new StatusRuntimeException(Status.UNKNOWN);
        }

        /* Generate new access token, new refresh token for user with Id, Role */
        String accessToken = authentication.generateAccessToken(user.getId(), user.getRole().toString());
        String refreshToken = authentication.generateRefreshToken(user.getId(), user.getRole().toString());
        return Triple.of(user.getUsername(), accessToken, refreshToken);
    }

    @Override
    public Triple<String, String, String> activateNewAccount(Long userId) {
        User user = userServiceBlockingStub.getUserById(Int64Value.of(userId));
        if (user.equals(User.getDefaultInstance())) {
            throw new StatusRuntimeException(Status.UNKNOWN);
        }

        /* Asynchronously activate user */
        Context context = Context.current().fork();
        context.run(() -> authServiceImplHelper.activateUserWithId(userId));

        /* Generate new access token, new refresh token for user with Id, Role */
        String accessToken = authentication.generateAccessToken(user.getId(), user.getRole().toString());
        String refreshToken = authentication.generateRefreshToken(user.getId(), user.getRole().toString());
        return Triple.of(user.getUsername(), accessToken, refreshToken);
    }
}