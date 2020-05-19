package com.bht.saigonparking.service.auth.service.impl;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.user.UserRole;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.service.auth.util.LoggingUtil;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.Setter;

/**
 *
 * @author bht
 */
@Component
public final class AuthServiceImplHelper {

    @Setter(onMethod = @__(@Autowired))
    private UserServiceGrpc.UserServiceStub userServiceStub;

    String generateAccessToken(@NotNull Long userId,
                               @NotNull UserRole userRole) {
        return "tempAccessToken";
    }

    void updateUserLastSignIn(Long userId) {
        userServiceStub.updateUserLastSignIn(Int64Value.of(userId), new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {
                LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                        String.format("updateUserLastSignIn(%d)", userId));
            }

            @Override
            public void onError(Throwable throwable) {
                LoggingUtil.log(Level.ERROR, "SERVICE", "Exception",
                        throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                // finish request
            }
        });
    }
}