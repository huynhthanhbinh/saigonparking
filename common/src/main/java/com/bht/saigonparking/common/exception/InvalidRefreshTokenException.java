package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if refresh is no longer stored in the database
 *
 * @author bht
 */
public final class InvalidRefreshTokenException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}