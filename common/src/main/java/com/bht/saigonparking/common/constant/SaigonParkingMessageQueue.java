package com.bht.saigonparking.common.constant;

import javax.validation.constraints.NotNull;

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


    public static String generateUserQueueName(@NotNull Long userId) {
        return String.format("user_%d_queue", userId);
    }

    public static String generateParkingLotExchangeName(@NotNull Long parkingLotId) {
        return String.format("parking_lot_%d_exchange", parkingLotId);
    }
}