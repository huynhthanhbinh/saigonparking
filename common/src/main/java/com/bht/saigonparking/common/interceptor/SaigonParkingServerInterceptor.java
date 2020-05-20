package com.bht.saigonparking.common.interceptor;

import static com.bht.saigonparking.common.auth.SaigonParkingAuthentication.AUTHORIZATION_KEY_NAME;
import static com.bht.saigonparking.common.auth.SaigonParkingAuthentication.INTERNAL_KEY_NAME;

import java.io.IOException;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;
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
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


/**
 *
 * This interceptor is using in gRPC server side
 * Except Authentication Service !!!!!!!!!!!!!!!
 *
 * This interceptor is using for checking if client's provided token is valid
 * This is using for Authentication and Authorization process in server's side
 *
 * Exception will be thrown if:
 *     - Token was expired  !!!
 *     - Token was modified !!!
 *
 * @author bht
 */
@Log4j2
@Getter
public final class SaigonParkingServerInterceptor implements ServerInterceptor {

    private SaigonParkingAuthentication authentication;
    private final Context.Key<String> roleContext = Context.key("role");
    private final Context.Key<Long> userIdContext = Context.key("userId");

    public static final Key<String> INTERNAL_SERVICE_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);
    public static final Key<String> AUTHORIZATION_KEY = Key.of(AUTHORIZATION_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);

    public SaigonParkingServerInterceptor() {
        try {
            authentication = new SaigonParkingAuthentication();

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
        String token = metadata.get(AUTHORIZATION_KEY);
        String internalServiceCodeString = metadata.get(INTERNAL_SERVICE_KEY);

        if (token == null && internalServiceCodeString == null) { /* spam requests */
            throw new StatusRuntimeException(Status.UNAUTHENTICATED);

        } else if (token != null) { /* external requests */
            try {
                SaigonParkingTokenBody tokenBody = authentication.parseJwtToken(token);
                role = tokenBody.getUserRole();
                userId = tokenBody.getUserId();

            } catch (ExpiredJwtException expiredJwtException) {
                throw new TokenExpiredException();

            } catch (SignatureException signatureException) {
                throw new TokenModifiedException();
            }

        } else { /* internal requests */
            role = "ADMIN";
            userId = 1L;
        }

        return Contexts.interceptCall(
                Context.current()
                        .withValue(roleContext, role)
                        .withValue(userIdContext, userId),
                serverCall,
                metadata,
                serverCallHandler);
    }
}