package com.bht.saigonparking.service.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;
import com.bht.saigonparking.common.base.BaseSaigonParkingAppConfiguration;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;
import com.bht.saigonparking.common.spring.SpringBeanLifeCycle;

/**
 *
 * @author bht
 */
@EnableAsync
@Configuration
@EnableTransactionManagement
@Import(ChannelConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE)
public class AppConfiguration extends BaseSaigonParkingAppConfiguration {

    public static final String BASE_PACKAGE = "com.bht.saigonparking.service.auth"; // base package of auth module, contains all

    @Bean
    public SpringBeanLifeCycle springBeanLifeCycle() {
        return new SpringBeanLifeCycle(BASE_PACKAGE);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SaigonParkingAuthentication saigonParkingBaseAuthentication() {
        return new SaigonParkingAuthenticationImpl();
    }

    @Bean
    public SaigonParkingClientInterceptor saigonParkingClientInterceptor() {
        return new SaigonParkingClientInterceptor(SaigonParkingClientInterceptor.INTERNAL_CODE_AUTH_SERVICE);
    }
}