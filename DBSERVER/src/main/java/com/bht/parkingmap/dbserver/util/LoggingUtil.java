package com.bht.parkingmap.dbserver.util;

import org.apache.logging.log4j.Level;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author bht
 */
@Log4j2
public final class LoggingUtil {

    private LoggingUtil() {
    }

    public static void log(Level logLevel, String key, String description, String value) {
        log.log(logLevel, format(key, description, value));
    }

    private static String format(String key, String description, String value) {
        return String.format("%-10s %-14s %s",
                "[" + key + "]",
                description + ":",
                value);
    }
}