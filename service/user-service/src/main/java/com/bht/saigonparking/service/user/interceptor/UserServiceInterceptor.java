package com.bht.saigonparking.service.user.interceptor;

import java.net.InetSocketAddress;
import java.util.Objects;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.service.user.base.BaseBean;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * This interceptor is using for checking if client's provided token is valid
 * This is using for Authentication and Authorization process in server's side
 *
 * @author bht
 */
@Component
@GRpcGlobalInterceptor
public final class UserServiceInterceptor implements ServerInterceptor, BaseBean {

    @Value("${token.user-id.decode.key}")
    private Long userIdDecodeKey;

    @Setter(onMethod = @__(@Autowired))
    private UserServiceInterceptorHelper userServiceInterceptorHelper;

    @Getter
    private final Context.Key<String> roleContext = Context.key("role");

    @Getter
    private final Context.Key<String> userIdContext = Context.key("userId");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {

        boolean isCallFromInternalService = userServiceInterceptorHelper.isOnSameNetwork(Objects
                .requireNonNull((InetSocketAddress) serverCall.getAttributes()
                        .get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR)).getHostString());

        Key<String> tokenKey = Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        String token = metadata.get(tokenKey);

        if (token == null && !isCallFromInternalService) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED);
        }

        String role = (isCallFromInternalService)
                ? "ADMIN"
                : "";

        String userId = (isCallFromInternalService)
                ? "1"
                : "";

        return Contexts.interceptCall(
                Context.current()
                        .withValue(roleContext, role)
                        .withValue(userIdContext, userId),
                serverCall,
                metadata,
                serverCallHandler);
    }
}