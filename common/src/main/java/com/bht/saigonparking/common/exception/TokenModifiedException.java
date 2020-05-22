package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if token had been modified by someone
 *
 * @author bht
 */
public final class TokenModifiedException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}