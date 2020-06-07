package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if user is not activated yet
 *
 * @author bht
 */
public final class UserNotActivatedException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}