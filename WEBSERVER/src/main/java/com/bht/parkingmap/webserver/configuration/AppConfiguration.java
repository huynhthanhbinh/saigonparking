package com.bht.parkingmap.webserver.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc;
import com.bht.parkingmap.api.proto.user.UserServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 *
 * @author bht
 */
@Configuration
@Import(MessageQueueConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE_SERVER)
public class AppConfiguration {

    static final String BASE_PACKAGE_SERVER = "com.bht.parkingmap.webserver"; // base package of SERVER module, contains all

    @Bean
    public ManagedChannel managedChannel(@Value("${dbserver.host}") String host,
                                         @Value("${dbserver.port.grpc}") int port) {

        return ManagedChannelBuilder                            // Channel is the abstraction to connect to a service endpoint
                .forAddress(host, port)                         // Port and Host of gRPC server, not of client !
                .usePlaintext()                                 // Let's use plaintext communication because we don't have certs
                .maxInboundMessageSize(10 * 1024 * 1024)        // 10KB * 1024 = 10MB --> max message size to transfer together
                .idleTimeout(5000, TimeUnit.MILLISECONDS)     // 5000 milliseconds / 5000 = 5 seconds --> request time-out
                .build();                                       // Builder-design-pattern --> using build method to get object
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(@Autowired ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ParkingLotServiceGrpc.ParkingLotServiceBlockingStub parkingLotServiceBlockingStub(@Autowired ManagedChannel channel) {
        return ParkingLotServiceGrpc.newBlockingStub(channel);
    }
}