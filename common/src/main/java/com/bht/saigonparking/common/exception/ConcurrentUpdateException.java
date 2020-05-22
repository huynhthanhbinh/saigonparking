package com.bht.saigonparking.common.exception;

/**
 *
 * This exception will be thrown on concurrent update entity
 * following optimistic locking mechanism by @version field,
 * as the version field of pre-update entity and current entity
 * is not equal. That means entity has just been modified
 * by some else transaction(tx) before this update transaction.
 *
 * Using for control concurrent update of entities
 *
 * @author bht
 */
public final class ConcurrentUpdateException extends RuntimeException {

    public ConcurrentUpdateException(String message) {
        super(message);
    }

    /* Disable print out stack trace */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}