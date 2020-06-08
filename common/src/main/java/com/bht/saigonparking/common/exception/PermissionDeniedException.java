package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown if user not have authorization to do an action
 *
 * @author bht
 */
public final class PermissionDeniedException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}