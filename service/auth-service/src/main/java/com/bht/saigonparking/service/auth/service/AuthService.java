package com.bht.saigonparking.service.auth.service;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.util.Pair;

import com.bht.saigonparking.api.grpc.auth.RegisterRequest;
import com.bht.saigonparking.api.grpc.user.UserRole;

/**
 *
 * @author bht
 */
public interface AuthService {

    /**
     *
     * @return triple of:
     * 1st: accessToken
     * 2nd: refreshToken
     */
    Pair<String, String> validateLogin(@NotEmpty String username,
                                       @NotEmpty String password,
                                       @NotNull UserRole userRole);

    /**
     *
     * @return user's email if succeed
     */
    String registerUser(@NotNull RegisterRequest request);

    /**
     *
     * @return user's email if succeed
     */
    String sendResetPasswordEmail(@NotEmpty String username);

    /**
     *
     * @return user's email if succeed
     */
    String sendActivateAccountEmail(@NotEmpty String username);

    /**
     *
     * @return triple of:
     * left:   username
     * middle: accessToken
     * right:  refreshToken
     */
    Triple<String, String, String> generateNewToken(@NotNull Long userId,
                                                    @NotNull Date currentExp,
                                                    @NotEmpty UUID currentTokenId,
                                                    boolean currentIsRefreshToken);

    /**
     *
     * @return triple of:
     * left:   username
     * middle: accessToken
     * right:  refreshToken
     */
    Triple<String, String, String> activateNewAccount(@NotNull Long userId,
                                                      @NotNull Date currentExp,
                                                      @NotEmpty UUID currentTokenId,
                                                      boolean currentIsRefreshToken);
}