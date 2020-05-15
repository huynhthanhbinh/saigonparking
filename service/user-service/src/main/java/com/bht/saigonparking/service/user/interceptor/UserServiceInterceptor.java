package com.bht.saigonparking.service.user.interceptor;

import java.net.InetSocketAddress;
import java.util.Objects;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import lombok.Setter;

/**
 *
 * @author bht
 */
@Component
@GRpcGlobalInterceptor
@Setter(onMethod = @__(@Autowired))
public final class UserServiceInterceptor implements ServerInterceptor {

    @Qualifier("localhost")
    private String localHost;

    @Getter
    private final Context.Key<String> roleContext = Context.key("Role");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {

        boolean isCallFromInternalService = isOnSameNetwork(Objects
                .requireNonNull((InetSocketAddress) serverCall.getAttributes()
                        .get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR)).getHostString());

        System.out.println("\n\n\n" + metadata + "\n\n\n");

        Key<String> authKey = Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        String authToken = metadata.get(authKey);

        if (authToken == null && !isCallFromInternalService) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED);
        }

        if (isCallFromInternalService) {
            // TODO: set role to admin
        }

        return Contexts.interceptCall(
                Context.current().withValue(roleContext, authToken),
                serverCall,
                metadata,
                serverCallHandler);
    }

    private boolean isOnSameNetwork(String otherAddress) {
        return otherAddress.substring(0, otherAddress.lastIndexOf('.'))
                .equals(localHost.substring(0, localHost.lastIndexOf('.')));
    }
}