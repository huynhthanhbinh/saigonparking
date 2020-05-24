package com.bht.saigonparking.service.auth.service.grpc;

import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import com.bht.saigonparking.api.grpc.auth.AuthServiceGrpc;
import com.bht.saigonparking.api.grpc.auth.RefreshTokenResponse;
import com.bht.saigonparking.api.grpc.auth.RegisterRequest;
import com.bht.saigonparking.api.grpc.auth.ValidateRequest;
import com.bht.saigonparking.api.grpc.auth.ValidateResponse;
import com.bht.saigonparking.api.grpc.auth.ValidateResponseType;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.auth.service.AuthService;
import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;

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
    private final SaigonParkingServerInterceptor serverInterceptor;

    @Override
    public void validateUser(ValidateRequest request, StreamObserver<ValidateResponse> responseObserver) {
        try {
            Pair<ValidateResponseType, String> validateResponsePair = authService.validateLogin(
                    request.getUsername(),
                    request.getPassword(),
                    request.getRole());

            ValidateResponse validateResponse = ValidateResponse.newBuilder()
                    .setResponse(validateResponsePair.getFirst())
                    .setAccessToken(validateResponsePair.getSecond())
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
    public void registerUser(RegisterRequest request, StreamObserver<Empty> responseObserver) {
        super.registerUser(request, responseObserver);
    }

    @Override
    public void sendResetPasswordEmail(StringValue request, StreamObserver<StringValue> responseObserver) {
        super.sendResetPasswordEmail(request, responseObserver);
    }

    @Override
    public void sendActivateAccountEmail(StringValue request, StreamObserver<StringValue> responseObserver) {
        super.sendActivateAccountEmail(request, responseObserver);
    }

    @Override
    public void generateNewToken(Empty request, StreamObserver<RefreshTokenResponse> responseObserver) {
        super.generateNewToken(request, responseObserver);
    }

    @Override
    public void activateNewAccount(Empty request, StreamObserver<RefreshTokenResponse> responseObserver) {
        super.activateNewAccount(request, responseObserver);
    }
}