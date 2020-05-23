package com.bht.saigonparking.service.auth.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.MAIL_TOPIC_ROUTING_KEY;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;
import com.bht.saigonparking.common.spring.SpringApplicationContext;
import com.bht.saigonparking.common.spring.SpringBeanLifeCycle;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Configuration
@Import(ChannelConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE)
public class AppConfiguration {

    static final String BASE_PACKAGE = "com.bht.saigonparking.service.auth"; // base package of auth module, contains all
    private final RabbitTemplate rabbitTemplate;

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
    public SaigonParkingClientInterceptor saigonParkingClientInterceptor() {
        rabbitTemplate.convertAndSend(MAIL_TOPIC_ROUTING_KEY, "Hello RabbitMQ");
        return new SaigonParkingClientInterceptor(SaigonParkingClientInterceptor.INTERNAL_CODE_AUTH_SERVICE);
    }
}