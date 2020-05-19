package com.bht.saigonparking.common.interceptor;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.Getter;


/**
 *
 * This interceptor is using in gRPC server side
 * Except Authentication Service !!!!!!!!!!!!!!!
 *
 * This interceptor is using for checking if client's provided token is valid
 * This is using for Authentication and Authorization process in server's side
 *
 * @author bht
 */
@Getter
public final class SaigonParkingServerInterceptor implements ServerInterceptor {

    private final Context.Key<String> roleContext = Context.key("role");
    private final Context.Key<Long> userIdContext = Context.key("userId");

    public static final String INTERNAL_KEY_NAME = "SaigonParkingInternal";
    public static final String AUTHORIZATION_KEY_NAME = "Authorization";
    public static final Key<String> INTERNAL_SERVICE_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);
    public static final Key<String> AUTHORIZATION_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);

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
            role = "";
            userId = 0L;

        } else { /* internal requests */
            role = "admin";
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