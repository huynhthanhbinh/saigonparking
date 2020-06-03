package com.bht.saigonparking.emulator.interceptor;

import org.springframework.stereotype.Component;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.MethodDescriptor;

/**
 *
 * @author bht
 */
@Component
public final class AuthTokenProvideInterceptor implements ClientInterceptor {

    private static final String INTERNAL_KEY_NAME = "saigon-parking-internal";
    private static final Key<String> INTERNAL_SERVICE_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(INTERNAL_SERVICE_KEY, "165305061220760000");
                super.start(responseListener, headers);
            }
        };
    }
}