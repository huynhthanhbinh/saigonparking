package com.bht.parkingmap.appserver.exception;

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
}