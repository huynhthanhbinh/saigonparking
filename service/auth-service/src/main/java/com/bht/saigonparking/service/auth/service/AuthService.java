package com.bht.saigonparking.service.auth.service;

import javax.validation.constraints.NotNull;

import org.springframework.data.util.Pair;

import com.bht.saigonparking.api.grpc.auth.ValidateResponseType;
import com.bht.saigonparking.api.grpc.user.UserRole;

/**
 *
 * @author bht
 */
public interface AuthService {

    Pair<ValidateResponseType, String> validateLogin(@NotNull String username,
                                                     @NotNull String password,
                                                     @NotNull UserRole userRole);
}