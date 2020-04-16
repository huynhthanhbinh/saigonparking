package com.bht.parkingmap.emulator.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
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
    @Bean
    public ManagedChannel managedChannel(@Value("${gateway.connection.host}") String host,
                                         @Value("${gateway.connection.port.grpc}") int port,
                                         @Value("${gateway.connection.idle-timeout}") int timeout,
                                         @Value("${gateway.connection.max-inbound-message-size}") int maxInBoundMessageSize,
                                         @Value("${gateway.connection.max-inbound-metadata-size}") int maxInBoundMetadataSize,
                                         @Value("${gateway.connection.certificate-path}") Resource certificate) throws IOException {

        NettyChannelBuilder channelBuilder = (host.equals("localhost"))                                 // if host is localhost means local development
                ? NettyChannelBuilder.forAddress(host, port).usePlaintext()                             // use plain-text on development environment
                : NettyChannelBuilder.forAddress(host, port).useTransportSecurity()                     // use SSL on production environment
                .sslContext(GrpcSslContexts.forClient().trustManager(certificate.getFile()).build());   // Build gRPC SSL context with cert

        return channelBuilder
                .keepAliveWithoutCalls(false)                                   // Close channel when client has already received response
                .idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 10000 milliseconds / 1000 = 5 seconds --> request time-out
                .maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
                .maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
                .build();                                                       // Build channel to communicate over gRPC
    }
}