package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if user password is incorrect
 *
 * @author bht
 */
public final class WrongPasswordException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}