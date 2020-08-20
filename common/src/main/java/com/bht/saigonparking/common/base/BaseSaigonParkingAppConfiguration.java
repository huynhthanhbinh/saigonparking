package com.bht.saigonparking.common.base;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bht.saigonparking.common.custom.CustomAsyncExceptionHandler;
import com.bht.saigonparking.common.spring.SpringApplicationContext;

/**
 *
 * @author bht
 */
public abstract class BaseSaigonParkingAppConfiguration implements AsyncConfigurer {

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Override
    public final Executor getAsyncExecutor() {
        return SpringApplicationContext.getBean(ThreadPoolTaskExecutor.class);
    }

    @Override
    public final AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}