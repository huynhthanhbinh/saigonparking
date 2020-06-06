package com.bht.saigonparking.common.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.util.Pair;

/**
 *
 * authentication with JWT token, support by JJWT library
 * using for Saigon Parking project only
 *
 * @author bht
 */
public interface SaigonParkingAuthentication {

    SaigonParkingTokenBody parseJwtToken(@NotEmpty String jsonWebToken);

    /* 1st: tokenId, 2nd: token */
    Pair<String, String> generateAccessToken(@NotNull Long userId, @NotEmpty String userRole);

    /* 1st: tokenId, 2nd: token */
    Pair<String, String> generateRefreshToken(@NotNull Long userId, @NotEmpty String userRole);

    /* 1st: tokenId, 2nd: token */
    Pair<String, String> generateActivateAccountToken(@NotNull Long userId, @NotEmpty String userRole);

    /* 1st: tokenId, 2nd: token */
    Pair<String, String> generateResetPasswordToken(@NotNull Long userId, @NotEmpty String userRole);
}