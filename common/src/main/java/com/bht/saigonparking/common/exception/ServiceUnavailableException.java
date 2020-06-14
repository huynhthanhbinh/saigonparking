package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if one service is unavailable at this moment
 *
 * @author bht
 */
public final class ServiceUnavailableException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}