package com.bht.saigonparking.common.custom;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.lang.NonNull;

import com.bht.saigonparking.common.util.LoggingUtil;

/**
 *
 * @author bht
 */
public final class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(@NonNull Throwable throwable,
                                        @NonNull Method method,
                                        @NonNull Object... objects) {

        LoggingUtil.log(Level.ERROR, "CustomAsyncExceptionHandler", "Exception",
                String.format("Method: %s, Exception: %s", method.getName(), throwable.getClass().getSimpleName()));
    }
}