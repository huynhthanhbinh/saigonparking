package com.bht.parkingmap.emulator.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceBlockingStub;
import com.bht.parkingmap.api.proto.user.UserServiceGrpc;
import com.bht.parkingmap.api.proto.user.UserServiceGrpc.UserServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 *
 * @author bht
 */
@Configuration
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE_SERVER)
public class AppConfiguration {

    static final String BASE_PACKAGE_SERVER = "com.bht.parkingmap.emulator"; // base package of SERVER module, contains all

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
                                         @Value("${gateway.connection.max-inbound-metadata-size}") int maxInBoundMetadataSize) {

        return ManagedChannelBuilder
                .forAddress(host, port)                                         // Port and Host of gRPC server, not of client !
                .usePlaintext()                                                 // Let's use plaintext communication because we don't have certs
                .keepAliveWithoutCalls(true)                                    // Keep gRPC always ready for new connection
                .idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 5000 milliseconds / 1000 = 5 seconds --> request time-out
                .maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
                .maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
                .build();                                                       // Builder-pattern --> using build method to get object
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