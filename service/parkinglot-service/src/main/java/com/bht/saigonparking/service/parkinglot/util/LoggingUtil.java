package com.bht.saigonparking.service.parkinglot.util;

import org.apache.logging.log4j.Level;

import lombok.extern.log4j.Log4j2;

/**
 * This is util class for logging purpose
 * Logging methods here have been customized
 * for using only in Parking Map Project here
 *
 * Current project using logging framework of Apache
 * That is Log4J - logging for java, which 'til now has 2 versions
 * Log4J  --> Log4J version 1.x
 * Log4J2 --> Log4J version 2.x, which consider to be the fastest java logging framework
 *
 * Log4J can be config through properties file:
 * log4j.properties / log4j,xml ,...   --> for version 1.x
 * log4j2.properties / log4j2.xml ,... --> for version 2.x
 *
 * Parking-map service is now logging on 2 side
 * Console logging + File logging (rolling)
 *
 * See the project's logging configurations
 * on classpath folder, file log4j2.properties.
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