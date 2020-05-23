package com.bht.saigonparking.common.constant;

/**
 *
 * @author bht
 */
public final class SaigonParkingMessageQueue {

    private SaigonParkingMessageQueue() {
    }

    public static final String TOPIC_EXCHANGE_NAME = "saigonparking.exchange";

    public static final String MAIL_QUEUE_NAME = "saigonparking.mail";
    public static final String USER_QUEUE_NAME = "saigonparking.user";
    public static final String PARKING_LOT_QUEUE_NAME = "saigonparking.parkinglot";

    public static final String MAIL_TOPIC_ROUTING_KEY = "saigonparking.mail";
    public static final String USER_TOPIC_ROUTING_KEY = "saigonparking.user";
    public static final String PARKING_LOT_ROUTING_KEY = "saigonparking.parkinglot";
}