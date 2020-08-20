package com.bht.saigonparking.common.base;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bht.saigonparking.common.custom.CustomAsyncExceptionHandler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *
 * @author bht
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class BaseSaigonParkingAppConfiguration implements AsyncConfigurer {

    @Override
    public final Executor getAsyncExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Override
    public final AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}