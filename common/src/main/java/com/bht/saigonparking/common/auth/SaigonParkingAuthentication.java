package com.bht.saigonparking.common.auth;

import java.time.temporal.ChronoUnit;

import org.springframework.data.util.Pair;

/**
 *
 * authentication with JWT token
 * support by JJWT library and
 * using for Saigon Parking project only
 *
 * @author bht
 */
public interface SaigonParkingAuthentication {

    /**
     *
     * @param userId real ID of user (before encrypt)
     * @param userRole role of user
     * @param timeAmount amount of time to add: Eg. 7
     * @param timeUnit unit of time amount: Eg. day
     *
     * @return new JWT token and sign with signature
     */
    String generateJwtToken(Long userId, String userRole, Integer timeAmount, ChronoUnit timeUnit);


    /**
     *
     * @param jwtBearerToken token string with Bearer prefix
     *
     * @return a pair of userId (key) & userRole (value)
     */
    Pair<Long, String> parseJwtToken(String jwtBearerToken);
}