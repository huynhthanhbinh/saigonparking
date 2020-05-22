package com.bht.saigonparking.common.auth;

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
     * @return new JWT token and sign with signature
     */
    String generateJwtToken(Long userId, String userRole);


    /**
     *
     * @param jwtBearerToken token string with Bearer prefix
     * @return a pair of userId (key) & userRole (value)
     */
    Pair<Long, String> parseJwtToken(String jwtBearerToken);
}