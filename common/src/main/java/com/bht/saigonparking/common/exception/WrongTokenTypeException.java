package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if token type is not suitable
 *
 * @author bht
 */
public final class WrongTokenTypeException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}