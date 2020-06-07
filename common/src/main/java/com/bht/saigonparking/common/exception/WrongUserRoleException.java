package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if user role is not suitable
 *
 * @author bht
 */
public final class WrongUserRoleException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}