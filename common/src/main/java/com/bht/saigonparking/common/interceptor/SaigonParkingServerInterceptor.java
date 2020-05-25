package com.bht.saigonparking.common.interceptor;

import static com.bht.saigonparking.common.interceptor.SaigonParkingTransactionalMetadata.AUTHORIZATION_KEY_NAME;
import static com.bht.saigonparking.common.interceptor.SaigonParkingTransactionalMetadata.INTERNAL_KEY_NAME;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.springframework.data.util.Pair;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;
import com.bht.saigonparking.common.exception.TokenExpiredException;
import com.bht.saigonparking.common.exception.TokenModifiedException;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AccessLevel;
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
@Getter
public final class SaigonParkingServerInterceptor implements ServerInterceptor {

    @Getter(AccessLevel.NONE)
    private SaigonParkingAuthentication authentication;

    /* skip checking token for these methods */
    @Getter(AccessLevel.NONE)
    private Set<String> nonProvideTokenMethodSet;

    private final Context.Key<String> roleContext = Context.key("role");
    private final Context.Key<Long> userIdContext = Context.key("userId");

    private static final Key<String> INTERNAL_SERVICE_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);
    private static final Key<String> AUTHORIZATION_KEY = Key.of(AUTHORIZATION_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);

    public SaigonParkingServerInterceptor() {
        this(Collections.emptySet());
    }

    public SaigonParkingServerInterceptor(Set<String> nonProvideTokenMethodSet) {
        try {
            authentication = new SaigonParkingAuthenticationImpl();
            this.nonProvideTokenMethodSet = nonProvideTokenMethodSet;

        } catch (IOException e) {
            log.error("Cannot find secret key file !");
        }
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        long userId;
        String role;

        /* get metadata from header of incoming request */
        String token = metadata.get(AUTHORIZATION_KEY);
        String internalServiceCodeString = metadata.get(INTERNAL_SERVICE_KEY);

        /* Method's full name, eg. com.bht.saigonparking.api.grpc.auth.AuthService/registerUser */
        String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();

        if (nonProvideTokenMethodSet.contains(fullMethodName)) { /* method skip check token */
            role = "UNRECOGNIZED";
            userId = 0L;

        } else if (token == null && internalServiceCodeString == null) { /* spam requests */
            throw new StatusRuntimeException(Status.UNAUTHENTICATED);

        } else if (token != null) { /* external requests */
            try {
                Pair<Long, String> tokenBody = authentication.parseJwtToken(token);
                userId = tokenBody.getFirst();
                role = tokenBody.getSecond();

            } catch (ExpiredJwtException expiredJwtException) {
                throw new TokenExpiredException();

            } catch (SignatureException signatureException) {
                throw new TokenModifiedException();
            }

        } else { /* internal requests */
            role = "ADMIN";
            userId = 1L;
        }

        ServerCall.Listener<ReqT> listener = Contexts
                .interceptCall(Context.current()
                                .withValue(roleContext, role)
                                .withValue(userIdContext, userId),
                        serverCall,
                        metadata,
                        serverCallHandler);

        /* listen to and throw exception with extra information to gRPC Client */
        return new ExceptionHandlingServerCallListener<>(listener, serverCall, metadata);
    }
}