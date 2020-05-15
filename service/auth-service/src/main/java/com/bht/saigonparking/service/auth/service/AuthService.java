package com.bht.saigonparking.service.auth.service;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.auth.ValidateResponseType;
import com.bht.saigonparking.api.grpc.user.UserRole;

/**
 *
 * @author bht
 */
public interface AuthService {

    ValidateResponseType validateLogin(@NotNull String username,
                                       @NotNull String password,
                                       @NotNull UserRole userRole);

    String generateAccessToken(@NotNull String username,
                               @NotNull UserRole userRole);
}