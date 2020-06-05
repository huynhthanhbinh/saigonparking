package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if token had been left empty
 *
 * @author bht
 */
public final class MissingTokenException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}