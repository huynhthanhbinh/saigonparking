package com.bht.saigonparking.common;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingAuthenticationImpl;

/**
 *
 * @author bht
 */
public final class TestCommon {

    public static void main(String[] args) {
        SaigonParkingAuthentication authentication = new SaigonParkingAuthenticationImpl();
        String token = authentication.generateAccessToken(4L, "CUSTOMER").getSecond();
        System.out.println(token);
        System.out.println(authentication.parseJwtToken(token));
    }
}