package com.bht.saigonparking.service.parkinglot.util;

import javassist.NotFoundException;

/**
 *
 * @author bht
 */
public final class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static NotFoundException throwEntityNotFoundException() {
        return new NotFoundException("Not found entity");
    }
}