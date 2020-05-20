package com.bht.saigonparking.common.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Definition of token's body
 * Not provide constructor and setter
 * To maintain immutability of token body object
 * To create new object, please use Builder()
 *
 * @author bht
 */
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class SaigonParkingTokenBody {

    /* ID of user */
    private final Long userId;

    /* Role of user */
    private final String userRole;
}