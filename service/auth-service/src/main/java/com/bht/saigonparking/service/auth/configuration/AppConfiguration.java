package com.bht.saigonparking.service.auth.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bht.saigonparking.common.auth.SaigonParkingBaseAuthentication;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;

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
    public SaigonParkingBaseAuthentication saigonParkingBaseAuthentication() throws IOException {
        return new SaigonParkingBaseAuthentication();
    }

    @Bean
    public SaigonParkingClientInterceptor saigonParkingClientInterceptor() {
        return new SaigonParkingClientInterceptor(SaigonParkingClientInterceptor.INTERNAL_CODE_AUTH_SERVICE);
    }
}