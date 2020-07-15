package com.bht.saigonparking.service.contact.interceptor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *
 * @author bht
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public final class WebSocketInterceptorConstraint {

    public static final String SAIGON_PARKING_USER_ID_KEY = "saigon_parking_user_id";
    public static final String SAIGON_PARKING_USER_ROLE_KEY = "saigon_parking_user_role";
    public static final String SAIGON_PARKING_PARKING_LOT_ID_KEY = "saigon_parking_parking_lot_id";
}