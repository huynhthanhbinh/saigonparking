package com.bht.saigonparking.service.auth.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;

/**
 * config gRPC channel
 *
 * @author bht
 */
@Component
public final class ChannelConfiguration {

    /**
     *
     * channel is the abstraction to connect to a service endpoint
     * note for gRPC stub:
     *      .newStub(channel)          --> nonblocking/asynchronous stub
     *      .newBlockingStub(channel)  --> blocking/synchronous stub
     */
    @Bean("userChannel")
    public ManagedChannel managedChannel(@Value("${service.user.host}") String host,
                                         @Value("${service.user.port.grpc}") int port,
                                         @Value("${service.user.idle-timeout}") int timeout,
                                         @Value("${service.user.max-inbound-message-size}") int maxInBoundMessageSize,
                                         @Value("${service.user.max-inbound-metadata-size}") int maxInBoundMetadataSize) {
        return NettyChannelBuilder
                .forAddress(host, port)                                         // build channel to server with server's address
                .usePlaintext()                                                 // use plain-text to communicate internally
                .keepAliveWithoutCalls(false)                                   // Close channel when client has already received response
                .idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 10000 milliseconds / 1000 = 10 seconds --> request time-out
                .maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
                .maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
                .build();                                                       // Build channel to communicate over gRPC
    }
}