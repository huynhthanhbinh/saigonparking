package com.bht.saigonparking.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *
 * @author bht
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public final class SaigonParkingMessageQueue {

    public static final String INTERNAL_EXCHANGE_NAME = "saigonparking-internal.exchange";
    public static final String CONTACT_EXCHANGE_NAME = "saigonparking-contact.exchange";

    public static final String MAIL_QUEUE_NAME = "saigonparking.mail";
    public static final String USER_QUEUE_NAME = "saigonparking.user";
    public static final String PARKING_LOT_QUEUE_NAME = "saigonparking.parkinglot";

    public static final String MAIL_TOPIC_ROUTING_KEY = "saigonparking.mail";
    public static final String USER_TOPIC_ROUTING_KEY = "saigonparking.user";
    public static final String PARKING_LOT_ROUTING_KEY = "saigonparking.parkinglot";
    public static final String CONTACT_ROUTING_KEY = "saigonparking.contact";
}