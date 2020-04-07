package com.bht.parkingmap.emulator.configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceBlockingStub;
import com.bht.parkingmap.api.proto.user.UserServiceGrpc;
import com.bht.parkingmap.api.proto.user.UserServiceGrpc.UserServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;

/**
 *
 * @author bht
 */
@Configuration
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE_SERVER)
public class AppConfiguration {

    static final String BASE_PACKAGE_SERVER = "com.bht.parkingmap.emulator"; // base package of EMULATOR module, contains all

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

        return NettyChannelBuilder                                              // Builder-pattern --> using build method to get object
                .forAddress(host, port)                                         // Port and Host of gRPC server, not of client !
                .useTransportSecurity()                                         // Mark that connection will be over SSL/TLS
                .sslContext(GrpcSslContexts                                     // Set SSL context
                        .forClient()                                            // SSL for client side (this)
                        .trustManager(certificate.getFile())                    // Trust only our certificate
                        .build())                                               // Build gRPC SSL context
                .keepAliveWithoutCalls(false)                                   // Close channel when client has already received response
                .idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 5000 milliseconds / 1000 = 5 seconds --> request time-out
                .maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
                .maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
                .build();                                                       // Build channel to communicate over gRPC
    }

    @Bean
    public UserServiceBlockingStub userServiceBlockingStub(@Autowired ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ParkingLotServiceBlockingStub parkingLotServiceBlockingStub(@Autowired ManagedChannel channel) {
        return ParkingLotServiceGrpc.newBlockingStub(channel);
    }
}