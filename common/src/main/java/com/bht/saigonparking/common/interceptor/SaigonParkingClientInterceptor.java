package com.bht.saigonparking.common.interceptor;

import static com.bht.saigonparking.common.interceptor.SaigonParkingTransactionalMetadata.INTERNAL_KEY_NAME;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.MethodDescriptor;
import lombok.AllArgsConstructor;

/**
 *
 * This interceptor is using in gRPC client side
 *
 * Each internal service has to use this common interceptor
 * So as other internal service can recognize without authentication
 * Each internal service has to init this as Spring Bean
 * So as to easily reuse later with {@code @Autowired } injecting bean
 *
 * Remember to use the one-argument public constructor instead
 * That is {@code InternalServiceProvideInterceptor(Long internalServiceCode) }
 * internalServiceCode is the code of the service using this interceptor
 * this code will be used for internal credentials recognized !
 *
 * @author bht
 */
@AllArgsConstructor
public final class SaigonParkingClientInterceptor implements ClientInterceptor {

    private final Long internalServiceCode;
    private static final Key<String> INTERNAL_SERVICE_KEY = Key.of(INTERNAL_KEY_NAME, Metadata.ASCII_STRING_MARSHALLER);

    public static final Long INTERNAL_CODE_AUTH_SERVICE = 165305061220760001L;
    public static final Long INTERNAL_CODE_USER_SERVICE = 165305061220760002L;
    public static final Long INTERNAL_CODE_PARKING_LOT_SERVICE = 165305061220760003L;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor,
                                                               CallOptions callOptions,
                                                               Channel channel) {

        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(INTERNAL_SERVICE_KEY, internalServiceCode.toString());
                super.start(responseListener, headers);
            }
        };
    }
}