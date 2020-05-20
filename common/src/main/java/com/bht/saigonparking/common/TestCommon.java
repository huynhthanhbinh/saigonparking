package com.bht.saigonparking.common;

import java.io.IOException;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author bht
 */
@Log4j2
final class TestCommon {
    public static void main(String[] args) throws IOException {
        SaigonParkingAuthentication authentication = new SaigonParkingAuthentication();
        String token = authentication.generateJwtToken(4L, "CUSTOMER");
        SaigonParkingTokenBody tokenBody = authentication.parseJwtToken(token);
        log.info(tokenBody);
    }
}