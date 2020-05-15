package com.bht.saigonparking.service.auth.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc.UserServiceBlockingStub;

import io.grpc.ManagedChannel;

/**
 *
 * @author bht
 */
@Configuration
@Import(ChannelConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE)
public class AppConfiguration {

    static final String BASE_PACKAGE = "com.bht.saigonparking.service.auth"; // base package of auth module, contains all

    // asynchronous user service stub
    @Bean
    public UserServiceGrpc.UserServiceStub userServiceStub(@Qualifier("userChannel") ManagedChannel channel) {
        return UserServiceGrpc.newStub(channel);
    }

    // synchronous user service stub
    @Bean
    public UserServiceBlockingStub userServiceBlockingStub(@Qualifier("userChannel") ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }
}