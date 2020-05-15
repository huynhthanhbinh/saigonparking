package com.bht.saigonparking.service.auth.service.impl;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.api.grpc.auth.ValidateResponseType;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.service.auth.service.AuthService;
import com.bht.saigonparking.service.auth.util.LoggingUtil;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {

    private final UserServiceGrpc.UserServiceStub userServiceStub;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public ValidateResponseType validateLogin(@NotNull String username,
                                              @NotNull String password,
                                              @NotNull UserRole userRole) {

        User user = userServiceBlockingStub.getUserByUsername(StringValue.of(username));

        if (!user.equals(User.getDefaultInstance())) {
            if (user.getRole().equals(userRole)) {
                if (Boolean.TRUE.equals(user.getIsActivated())) {
                    if (password.equals(user.getPassword())) {
                        userServiceStub.updateUserLastSignIn(Int64Value.of(user.getId()), new StreamObserver<Empty>() {
                            @Override
                            public void onNext(Empty empty) {
                                LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                                        String.format("updateUserLastSignIn(%d)", user.getId()));
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", throwable.getMessage());
                            }

                            @Override
                            public void onCompleted() {
                                // finish request
                            }
                        });
                        return ValidateResponseType.AUTHENTICATED;
                    }
                    return ValidateResponseType.INCORRECT;
                }
                return ValidateResponseType.INACTIVATED;
            }
            return ValidateResponseType.DISALLOWED;
        }
        return ValidateResponseType.NON_EXIST;
    }

    @Override
    public String generateAccessToken(@NotNull String username, @NotNull UserRole userRole) {
        return "tempAccessToken";
    }
}