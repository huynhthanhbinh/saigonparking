package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if token had been expired
 *
 * @author bht
 */
public final class TokenExpiredException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}