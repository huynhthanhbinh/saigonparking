package com.bht.saigonparking.service.auth.interceptor;

import static com.bht.saigonparking.common.constant.SaigonParkingTransactionalMetadata.AUTHORIZATION_KEY_NAME;
import static com.bht.saigonparking.common.constant.SaigonParkingTransactionalMetadata.INTERNAL_KEY_NAME;

import java.util.Map;
import java.util.Set;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;
import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;
import com.bht.saigonparking.common.auth.SaigonParkingTokenType;
import com.bht.saigonparking.common.interceptor.SaigonParkingCustomizedServerCall;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


/**
 *
 * This interceptor is using in gRPC server side
 * Customized Saigon Parking Server Interceptor for Auth Service only !!!!!!!
 *
 * This interceptor is using for checking if client's provided token is valid
 * This is using for Authentication and Authorization process in server's side
 *
 * @author bht
 */
@Log4j2
@GRpcGlobalInterceptor
public final class AuthServiceInterceptor implements ServerInterceptor {

    private final SaigonParkingAuthentication authentication;
    private final Set<String> nonProvideTokenMethodSet;
    private final Map<Class<? extends Throwable>, String> errorCodeMap;

    @Getter
    private final Context.Key<Long> userIdContext = Context.key("userId");
    @Getter
    private final Context.Key<String> userRoleContext = Context.key("userRole");
    @Getter
    private final Context.Key<String> tokenIdContext = Context.key("tokenId");
    @Getter
    private final Context.Key<SaigonParkingTokenType> tokenTypeContext = Context.key("tokenType");

    private static final Key<String> INTERNAL_SERVICE_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);
    private static final Key<String> AUTHORIZATION_KEY = Key.of(AUTHORIZATION_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);

    public AuthServiceInterceptor() {
        authentication = new SaigonParkingAuthenticationImpl();

        nonProvideTokenMethodSet = new ImmutableSet.Builder<String>()
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/validateUser")
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/registerUser")
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/sendResetPasswordEmail")
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/sendActivateAccountEmail")
                .build();

        errorCodeMap = new ImmutableMap.Builder<Class<? extends Throwable>, String>()
                .build();
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {

        ServerCall.Listener<ReqT> newCallListener = new ServerCall.Listener<ReqT>() {
        };

        long userId;
        String userRole;
        String tokenId;
        SaigonParkingTokenType tokenType;

        /* get metadata from header of incoming request */
        String token = metadata.get(AUTHORIZATION_KEY);
        String internalServiceCodeString = metadata.get(INTERNAL_SERVICE_KEY);

        /* Method's full name, eg. com.bht.saigonparking.api.grpc.auth.AuthService/registerUser */
        String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();

        if (nonProvideTokenMethodSet.contains(fullMethodName)) { /* method skip check token => AuthService */
            userId = 0L;
            userRole = "UNRECOGNIZED";
            tokenId = "";
            tokenType = null;

        } else if (token == null && internalServiceCodeString == null) { /* spam requests */
            serverCall.close(Status.UNAUTHENTICATED.withDescription("SPE#00004"), metadata);
            return newCallListener;

        } else if (token != null) { /* external requests */
            try {
                SaigonParkingTokenBody tokenBody = authentication.parseJwtToken(token);
                userId = tokenBody.getUserId();
                userRole = tokenBody.getUserRole();
                tokenId = tokenBody.getTokenId();
                tokenType = tokenBody.getTokenType();

            } catch (ExpiredJwtException expiredJwtException) {
                serverCall.close(Status.UNAUTHENTICATED.withDescription("SPE#00001"), metadata);
                return newCallListener;

            } catch (SignatureException signatureException) {
                serverCall.close(Status.UNAUTHENTICATED.withDescription("SPE#00002"), metadata);
                return newCallListener;

            } catch (MalformedJwtException malformedJwtException) {
                serverCall.close(Status.UNAUTHENTICATED.withDescription("SPE#00003"), metadata);
                return newCallListener;

            } catch (Exception exception) {
                serverCall.close(Status.INTERNAL.withDescription("SPE#00000"), metadata);
                return newCallListener;
            }

        } else { /* internal requests */
            userId = 1L;
            userRole = "ADMIN";
            tokenId = "";
            tokenType = null;
        }

        ServerCall<ReqT, RespT> wrappedServerCall = new SaigonParkingCustomizedServerCall<>(serverCall, errorCodeMap);
        return Contexts.interceptCall(Context.current()
                        .withValue(userIdContext, userId)
                        .withValue(userRoleContext, userRole)
                        .withValue(tokenIdContext, tokenId)
                        .withValue(tokenTypeContext, tokenType),
                wrappedServerCall,
                metadata,
                serverCallHandler);
    }
}