package com.bht.saigonparking.service.contact.configuration;

import javax.persistence.EntityNotFoundException;

import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.bht.saigonparking.common.annotation.InheritedComponent;
import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;
import com.bht.saigonparking.common.base.BaseSaigonParkingAppConfiguration;
import com.bht.saigonparking.common.exception.PermissionDeniedException;
import com.bht.saigonparking.common.exception.UsernameNotMatchException;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;
import com.bht.saigonparking.common.interceptor.SaigonParkingServerInterceptor;
import com.bht.saigonparking.common.spring.SpringBeanLifeCycle;
import com.google.common.collect.ImmutableMap;
import com.google.zxing.qrcode.QRCodeWriter;


/**
 *
 * @author bht
 */
@EnableAsync
@Configuration
@EnableWebSocket
@EnableTransactionManagement
@Import({WebSocketConfiguration.class, MessageQueueConfiguration.class, ChannelConfiguration.class})
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE, includeFilters = @ComponentScan.Filter(InheritedComponent.class))
public class AppConfiguration extends BaseSaigonParkingAppConfiguration {

    public static final String BASE_PACKAGE = "com.bht.saigonparking.service.contact";

    @Bean
    public QRCodeWriter qrCodeWriter() {
        return new QRCodeWriter();
    }

    @Bean
    public SpringBeanLifeCycle springBeanLifeCycle() {
        return new SpringBeanLifeCycle(BASE_PACKAGE);
    }

    @Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    public SaigonParkingAuthentication saigonParkingBaseAuthentication() {
        return new SaigonParkingAuthenticationImpl();
    }

    @Bean
    public SaigonParkingClientInterceptor saigonParkingClientInterceptor() {
        return new SaigonParkingClientInterceptor(SaigonParkingClientInterceptor.INTERNAL_CODE_CONTACT_SERVICE);
    }

    @Bean
    @GRpcGlobalInterceptor
    public SaigonParkingServerInterceptor saigonParkingServerInterceptor() {
        return new SaigonParkingServerInterceptor(new ImmutableMap.Builder<Class<? extends Throwable>, String>()
                .put(EntityNotFoundException.class, "SPE#00008")
                .put(DataIntegrityViolationException.class, "SPE#00009")
                .put(UsernameNotMatchException.class, "SPE#00014")
                .put(PermissionDeniedException.class, "SPE#00015")
                .put(ObjectOptimisticLockingFailureException.class, "SPE#00016")
                .put(EmptyResultDataAccessException.class, "SPE#00018")
                .build());
    }
}