package com.bht.parkingmap.util;

/**
 *
 * @author bht
 */
public final class LoggingUtil {

    private LoggingUtil() {
    }

    public static String format(String key, String description, String value) {
        return String.format("%-10s %-14s %s", "[" + key + "]", description + ":", value);
    }
}