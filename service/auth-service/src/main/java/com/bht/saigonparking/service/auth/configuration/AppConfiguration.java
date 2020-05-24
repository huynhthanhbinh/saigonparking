package com.bht.saigonparking.service.auth.configuration;

import java.io.IOException;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.spring.SpringApplicationContext;
import com.bht.saigonparking.common.spring.SpringBeanLifeCycle;
import com.google.common.collect.ImmutableSet;

/**
 *
 * @author bht
 */
@Configuration
@Import(ChannelConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE)
public class AppConfiguration {

    static final String BASE_PACKAGE = "com.bht.saigonparking.service.auth"; // base package of auth module, contains all

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean
    public SpringBeanLifeCycle springBeanLifeCycle() {
        return new SpringBeanLifeCycle(BASE_PACKAGE);
    }

    @Bean
    public SaigonParkingAuthentication saigonParkingBaseAuthentication() throws IOException {
        return new SaigonParkingAuthenticationImpl();
    }

    @Bean
    @GRpcGlobalInterceptor
    public SaigonParkingServerInterceptor saigonParkingServerInterceptor() {
        return new SaigonParkingServerInterceptor(new ImmutableSet.Builder<String>()
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/validateUser")
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/registerUser")
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/sendResetPasswordEmail")
                .add("com.bht.saigonparking.api.grpc.auth.AuthService/sendActivateAccountEmail")
                .build());
    }

    @Bean
    public SaigonParkingClientInterceptor saigonParkingClientInterceptor() {
        return new SaigonParkingClientInterceptor(SaigonParkingClientInterceptor.INTERNAL_CODE_AUTH_SERVICE);
    }
}