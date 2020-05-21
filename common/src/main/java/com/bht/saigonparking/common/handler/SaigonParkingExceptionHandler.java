package com.bht.saigonparking.common.handler;

import org.apache.logging.log4j.Level;

import com.bht.saigonparking.common.util.LoggingUtil;

/**
 *
 * @author bht
 */
public final class SaigonParkingExceptionHandler implements Thread.UncaughtExceptionHandler {

    public SaigonParkingExceptionHandler() {
        String message = "Register Saigon Parking global exception handler";
        LoggingUtil.log(Level.INFO, "GLOBAL", "Exception handler", message);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LoggingUtil.log(Level.ERROR, "GLOBAL", "Exception", e.getClass().getSimpleName());
    }
}