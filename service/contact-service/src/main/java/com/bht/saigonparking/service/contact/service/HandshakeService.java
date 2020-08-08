package com.bht.saigonparking.service.contact.service;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;

/**
 *
 * @author bht
 */
public interface HandshakeService {

    /**
     * if JWT token is successfully parsed, it means authentication is success
     * after authentication succeeded, this method will be run
     * and return a new attribute map for re-assign purpose
     *
     * @param tokenBody all fields parsed from JWT
     * @param mustConsumeFromQueue must register a queue and listen to it or not
     * @return Map of session attributes
     */
    Map<String, Object> postAuthentication(@NotNull SaigonParkingTokenBody tokenBody, boolean mustConsumeFromQueue);
}