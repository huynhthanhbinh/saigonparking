package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if username and user id is not of the same user
 *
 * @author bht
 */
public final class UsernameNotMatchException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}