package com.bht.saigonparking.emulator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bht.saigonparking.api.grpc.auth.AuthServiceGrpc;
import com.bht.saigonparking.api.grpc.booking.BookingServiceGrpc;
import com.bht.saigonparking.api.grpc.contact.ContactServiceGrpc;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceBlockingStub;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc.UserServiceBlockingStub;

import io.grpc.ManagedChannel;

/**
 *
 * @author bht
 */
@Configuration
@Import(ChannelConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE_SERVER)
public class AppConfiguration {

    static final String BASE_PACKAGE_SERVER = "com.bht.saigonparking.emulator"; // base package of EMULATOR module, contains all

    @Bean
    public UserServiceBlockingStub userServiceBlockingStub(@Autowired ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ParkingLotServiceBlockingStub parkingLotServiceBlockingStub(@Autowired ManagedChannel channel) {
        return ParkingLotServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub(@Autowired ManagedChannel channel) {
        return AuthServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ContactServiceGrpc.ContactServiceBlockingStub contactServiceBlockingStub(@Autowired ManagedChannel channel) {
        return ContactServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub(@Autowired ManagedChannel channel) {
        return BookingServiceGrpc.newBlockingStub(channel);
    }
}