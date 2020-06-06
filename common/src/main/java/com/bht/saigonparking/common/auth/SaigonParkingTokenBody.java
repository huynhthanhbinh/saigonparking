package com.bht.saigonparking.common.auth;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author bht
 */
@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class SaigonParkingTokenBody {

    private final String tokenId;
    private final SaigonParkingTokenType tokenType;
    private final Long userId;
    private final String userRole;
    private final Date exp;
}