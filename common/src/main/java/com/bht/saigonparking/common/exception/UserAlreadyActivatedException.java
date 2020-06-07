package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if user is already activated
 *
 * @author bht
 */
public final class UserAlreadyActivatedException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}