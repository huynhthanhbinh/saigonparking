package com.bht.saigonparking.service.mail.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bht.saigonparking.common.spring.SpringApplicationContext;
import com.bht.saigonparking.common.spring.SpringBeanLifeCycle;

/**
 *
 * @author bht
 */
@Configuration
@Import(MessageQueueConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE)
public class AppConfiguration {

    static final String BASE_PACKAGE = "com.bht.saigonparking.service.mail"; // base package of auth module, contains all

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean
    public SpringBeanLifeCycle springBeanLifeCycle() {
        return new SpringBeanLifeCycle(BASE_PACKAGE);
    }
}