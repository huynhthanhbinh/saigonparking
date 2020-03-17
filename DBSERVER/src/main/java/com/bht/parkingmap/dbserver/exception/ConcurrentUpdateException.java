package com.bht.parkingmap.dbserver.exception;

/**
 *
 * @author bht
 */
public final class ConcurrentUpdateException extends RuntimeException {

    public ConcurrentUpdateException(String message) {
        super(message);
    }
}