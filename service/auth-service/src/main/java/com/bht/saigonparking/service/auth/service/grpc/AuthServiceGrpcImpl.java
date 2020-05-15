package com.bht.saigonparking.service.auth.service.grpc;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.saigonparking.api.grpc.auth.AuthServiceGrpc;
import com.bht.saigonparking.api.grpc.auth.RegisterRequest;
import com.bht.saigonparking.api.grpc.auth.RegisterResponse;
import com.bht.saigonparking.api.grpc.auth.ValidateRequest;
import com.bht.saigonparking.api.grpc.auth.ValidateResponse;
import com.bht.saigonparking.api.grpc.auth.ValidateResponseType;
import com.bht.saigonparking.service.auth.service.AuthService;
import com.bht.saigonparking.service.auth.util.LoggingUtil;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@GRpcService
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class AuthServiceGrpcImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final AuthService authService;

    @Override
    public void validateUser(ValidateRequest request, StreamObserver<ValidateResponse> responseObserver) {
        try {
            ValidateResponseType validateResponseType = authService.validateLogin(
                    request.getUsername(),
                    request.getPassword(),
                    request.getRole());

            String accessToken = validateResponseType.equals(ValidateResponseType.AUTHENTICATED)
                    ? authService.generateAccessToken(request.getUsername(), request.getRole())
                    : "";

            ValidateResponse validateResponse = ValidateResponse.newBuilder()
                    .setResponse(validateResponseType)
                    .setAccessToken(accessToken)
                    .build();

            responseObserver.onNext(validateResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("validateUser(%s, %s, %s): %s",
                            request.getUsername(), request.getPassword(), request.getRole(), validateResponse.getResponse()));

        } catch (Exception exception) {
            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("validateUser(%s, %s, %s)",
                            request.getUsername(), request.getPassword(), request.getRole()));
        }
    }

    @Override
    public void registerUser(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        super.registerUser(request, responseObserver);
    }
}