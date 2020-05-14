package com.bht.saigonparking.service.user.interceptor;

import java.net.InetSocketAddress;
import java.util.Objects;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.stereotype.Component;

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

/**
 *
 * @author bht
 */
@Component
@GRpcGlobalInterceptor
public final class UserServiceInterceptor implements ServerInterceptor {

    @Getter
    private final Context.Key<String> tokenContext = Context.key("Authorization");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {

        InetSocketAddress clientAddress = (InetSocketAddress) serverCall.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
        System.out.println("Network address: " + Objects.requireNonNull(clientAddress).getHostString());
        System.out.println("Is same network: " + Objects.requireNonNull(clientAddress).getHostString().startsWith("172"));

        Key<String> authKey = Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        String authToken = metadata.get(authKey);

        if (authToken == null) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED);
        }

        return Contexts.interceptCall(
                Context.current().withValue(tokenContext, authToken),
                serverCall,
                metadata,
                serverCallHandler);
    }
}