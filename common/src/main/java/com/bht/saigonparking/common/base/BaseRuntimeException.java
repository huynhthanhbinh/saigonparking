package com.bht.saigonparking.common.base;

/**
 *
 * @author bht
 */
public abstract class BaseRuntimeException extends RuntimeException {

    /* Disable print out stack trace */
    @Override
    public final synchronized Throwable fillInStackTrace() {
        return this;
    }
}