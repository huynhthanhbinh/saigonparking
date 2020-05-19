package com.bht.saigonparking.service.parkinglot.configuration;

import java.io.IOException;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bht.saigonparking.common.auth.SaigonParkingBaseAuthentication;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.service.parkinglot.annotation.InheritedComponent;

/**
 *
 * @author bht
 */
@Configuration
@EnableTransactionManagement
@SuppressWarnings("squid:S1118")
@Import({AwsConfiguration.class, ChannelConfiguration.class})
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE,
        includeFilters = @ComponentScan.Filter(InheritedComponent.class))
public class AppConfiguration {

    public static final String BASE_PACKAGE = "com.bht.saigonparking.service.parkinglot";

    @Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    public SaigonParkingBaseAuthentication saigonParkingBaseAuthentication() throws IOException {
        return new SaigonParkingBaseAuthentication();
    }

    @Bean
    public SaigonParkingClientInterceptor saigonParkingClientInterceptor() {
        return new SaigonParkingClientInterceptor(SaigonParkingClientInterceptor.INTERNAL_CODE_PARKING_LOT_SERVICE);
    }

    @Bean
    @GRpcGlobalInterceptor
    public SaigonParkingServerInterceptor saigonParkingServerInterceptor() {
        return new SaigonParkingServerInterceptor();
    }
}