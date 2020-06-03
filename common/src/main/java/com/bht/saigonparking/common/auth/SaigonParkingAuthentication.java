package com.bht.saigonparking.common.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * authentication with JWT token
 * support by JJWT library and
 * using for Saigon Parking project only
 *
 * @author bht
 */
public interface SaigonParkingAuthentication {

    SaigonParkingTokenBody parseJwtToken(@NotEmpty String jsonWebToken);

    String generateAccessToken(@NotNull Long userId, @NotEmpty String userRole);

    String generateRefreshToken(@NotNull Long userId, @NotEmpty String userRole);

    String generateActivateAccountToken(@NotNull Long userId, @NotEmpty String userRole);

    String generateResetPasswordToken(@NotNull Long userId, @NotEmpty String userRole);
}