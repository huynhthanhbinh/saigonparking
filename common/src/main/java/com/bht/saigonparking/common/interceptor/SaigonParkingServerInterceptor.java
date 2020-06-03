package com.bht.saigonparking.common.interceptor;

import static com.bht.saigonparking.common.constant.SaigonParkingTransactionalMetadata.AUTHORIZATION_KEY_NAME;
import static com.bht.saigonparking.common.constant.SaigonParkingTransactionalMetadata.INTERNAL_KEY_NAME;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;
import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;

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
 *
 * This interceptor is using for checking if client's provided token is valid
 * This is using for Authentication and Authorization process in server's side
 *
 * @author bht
 */
@Log4j2
public final class SaigonParkingServerInterceptor implements ServerInterceptor {

    private final SaigonParkingAuthentication authentication;
    private final Set<String> nonProvideTokenMethodSet;
    private final Map<Class<? extends Throwable>, String> errorCodeMap;

    @Getter
    private final Context.Key<String> roleContext = Context.key("role");
    @Getter
    private final Context.Key<Long> userIdContext = Context.key("userId");

    private static final Key<String> INTERNAL_SERVICE_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);
    private static final Key<String> AUTHORIZATION_KEY = Key.of(AUTHORIZATION_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);

    public SaigonParkingServerInterceptor() {
        this(Collections.emptySet(), Collections.emptyMap());
    }

    public SaigonParkingServerInterceptor(Map<Class<? extends Throwable>, String> errorCodeMap) {
        this(Collections.emptySet(), errorCodeMap);
    }

    public SaigonParkingServerInterceptor(Set<String> nonProvideTokenMethodSet,
                                          Map<Class<? extends Throwable>, String> errorCodeMap) {
        authentication = new SaigonParkingAuthenticationImpl();
        this.errorCodeMap = errorCodeMap;
        this.nonProvideTokenMethodSet = nonProvideTokenMethodSet;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {

        ServerCall.Listener<ReqT> newCallListener = new ServerCall.Listener<ReqT>() {
        };

        long userId;
        String role;

        /* get metadata from header of incoming request */
        String token = metadata.get(AUTHORIZATION_KEY);
        String internalServiceCodeString = metadata.get(INTERNAL_SERVICE_KEY);

        /* Method's full name, eg. com.bht.saigonparking.api.grpc.auth.AuthService/registerUser */
        String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();

        if (nonProvideTokenMethodSet.contains(fullMethodName)) { /* method skip check token => AuthService */
            role = "UNRECOGNIZED";
            userId = 0L;

        } else if (token == null && internalServiceCodeString == null) { /* spam requests */
            serverCall.close(Status.UNAUTHENTICATED.withDescription("SPE#00004"), metadata);
            return newCallListener;

        } else if (token != null) { /* external requests */
            try {
                SaigonParkingTokenBody tokenBody = authentication.parseJwtToken(token);
                userId = tokenBody.getUserId();
                role = tokenBody.getUserRole();

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
            role = "ADMIN";
            userId = 1L;
        }

        ServerCall<ReqT, RespT> wrappedServerCall = new SaigonParkingCustomizedServerCall<>(serverCall, errorCodeMap);
        return Contexts.interceptCall(Context.current()
                        .withValue(roleContext, role)
                        .withValue(userIdContext, userId),
                wrappedServerCall,
                metadata,
                serverCallHandler);
    }
}