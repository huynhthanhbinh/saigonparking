package com.bht.saigonparking.service.auth.service.grpc;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import com.bht.saigonparking.api.grpc.auth.AuthServiceGrpc;
import com.bht.saigonparking.api.grpc.auth.RefreshTokenResponse;
import com.bht.saigonparking.api.grpc.auth.RegisterRequest;
import com.bht.saigonparking.api.grpc.auth.ValidateRequest;
import com.bht.saigonparking.api.grpc.auth.ValidateResponse;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.common.auth.SaigonParkingTokenType;
import com.bht.saigonparking.common.exception.WrongTokenTypeException;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.auth.interceptor.AuthServiceInterceptor;
import com.bht.saigonparking.service.auth.service.AuthService;
import com.google.protobuf.BoolValue;
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
    private final AuthServiceInterceptor authServiceInterceptor;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public void checkUsernameAlreadyExist(StringValue request, StreamObserver<BoolValue> responseObserver) {
        try {
            BoolValue isUsernameAlreadyExist = userServiceBlockingStub.checkUsernameAlreadyExist(request);

            responseObserver.onNext(isUsernameAlreadyExist);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("checkUsernameAlreadyExist(%s): %b", request.getValue(), isUsernameAlreadyExist.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("checkUsernameAlreadyExist(%s)", request.getValue()));
        }
    }

    @Override
    public void checkEmailAlreadyExist(StringValue request, StreamObserver<BoolValue> responseObserver) {
        try {
            BoolValue isEmailAlreadyExist = userServiceBlockingStub.checkEmailAlreadyExist(request);

            responseObserver.onNext(isEmailAlreadyExist);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("checkEmailAlreadyExist(%s): %b", request.getValue(), isEmailAlreadyExist.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("checkEmailAlreadyExist(%s)", request.getValue()));
        }
    }

    @Override
    public void validateUser(ValidateRequest request, StreamObserver<ValidateResponse> responseObserver) {
        try {
            Pair<String, String> validateResponseTriple = authService.validateLogin(
                    request.getUsername(),
                    request.getPassword(),
                    request.getRole());

            ValidateResponse validateResponse = ValidateResponse.newBuilder()
                    .setAccessToken(validateResponseTriple.getFirst())
                    .setRefreshToken(validateResponseTriple.getSecond())
                    .build();

            responseObserver.onNext(validateResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("validateUser(%s, %s)", request.getUsername(), request.getRole()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("validateUser(%s, %s, %s)",
                            request.getUsername(), request.getPassword(), request.getRole()));
        }
    }

    @Override
    public void registerUser(RegisterRequest request, StreamObserver<StringValue> responseObserver) {
        try {
            StringValue email = StringValue.of(authService.registerUser(request));

            responseObserver.onNext(email);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("registerUser(%s)", request.getUsername()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("registerUser(%s)", request.getUsername()));
        }
    }

    @Override
    public void sendResetPasswordEmail(StringValue request, StreamObserver<StringValue> responseObserver) {
        try {
            StringValue email = StringValue.of(authService
                    .sendResetPasswordEmail(request.getValue()));

            responseObserver.onNext(email);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("sendResetPasswordEmail(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("sendResetPasswordEmail(%s)", request.getValue()));
        }
    }

    @Override
    public void sendActivateAccountEmail(StringValue request, StreamObserver<StringValue> responseObserver) {
        try {
            StringValue email = StringValue.of(authService
                    .sendActivateAccountEmail(request.getValue()));

            responseObserver.onNext(email);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("sendActivateAccountEmail(%s)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("sendActivateAccountEmail(%s)", request.getValue()));
        }
    }

    @Override
    public void generateNewToken(Empty request, StreamObserver<RefreshTokenResponse> responseObserver) {
        Long userId = authServiceInterceptor.getUserIdContext().get();
        try {
            SaigonParkingTokenType tokenType = authServiceInterceptor.getTokenTypeContext().get();

            if (tokenType.equals(SaigonParkingTokenType.ACCESS_TOKEN)) {
                throw new WrongTokenTypeException();
            }

            Triple<String, String, String> refreshTokenTriple = authService.generateNewToken(
                    userId,
                    authServiceInterceptor.getExpContext().get(),
                    authServiceInterceptor.getTokenIdContext().get(),
                    tokenType.equals(SaigonParkingTokenType.REFRESH_TOKEN));

            RefreshTokenResponse refreshTokenResponse = RefreshTokenResponse.newBuilder()
                    .setUsername(refreshTokenTriple.getLeft())
                    .setAccessToken(refreshTokenTriple.getMiddle())
                    .setRefreshToken(refreshTokenTriple.getRight())
                    .build();

            responseObserver.onNext(refreshTokenResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("generateNewToken(%d)", userId));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("generateNewToken(%d)", userId));
        }
    }

    @Override
    public void activateNewAccount(Empty request, StreamObserver<RefreshTokenResponse> responseObserver) {
        Long userId = authServiceInterceptor.getUserIdContext().get();
        try {
            SaigonParkingTokenType tokenType = authServiceInterceptor.getTokenTypeContext().get();

            if (!tokenType.equals(SaigonParkingTokenType.ACTIVATE_TOKEN)) {
                throw new WrongTokenTypeException();
            }

            Triple<String, String, String> refreshTokenTriple = authService.activateNewAccount(
                    userId,
                    authServiceInterceptor.getExpContext().get(),
                    authServiceInterceptor.getTokenIdContext().get(),
                    false);

            RefreshTokenResponse refreshTokenResponse = RefreshTokenResponse.newBuilder()
                    .setUsername(refreshTokenTriple.getLeft())
                    .setAccessToken(refreshTokenTriple.getMiddle())
                    .setRefreshToken(refreshTokenTriple.getRight())
                    .build();

            responseObserver.onNext(refreshTokenResponse);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("activateNewAccount(%d)", userId));

        } catch (Exception exception) {

            responseObserver.onError(exception);

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("activateNewAccount(%d)", userId));
        }
    }
}