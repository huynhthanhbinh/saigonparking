package com.bht.saigonparking.common.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.common.exception.IncorrectQueueNameException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *
 * @author bht
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public final class SaigonParkingMessageQueue {

    public static final String CONTACT_EXCHANGE_NAME = "saigonparking-contact.exchange";
    public static final String INTERNAL_EXCHANGE_NAME = "saigonparking-internal.exchange";

    public static final String BOOKING_QUEUE_NAME = "saigonparking.booking";
    public static final String MAIL_QUEUE_NAME = "saigonparking.mail";
    public static final String USER_QUEUE_NAME = "saigonparking.user";
    public static final String PARKING_LOT_QUEUE_NAME = "saigonparking.parkinglot";

    public static final String BOOKING_TOPIC_ROUTING_KEY = "saigonparking.booking";
    public static final String MAIL_TOPIC_ROUTING_KEY = "saigonparking.mail";
    public static final String USER_TOPIC_ROUTING_KEY = "saigonparking.user";
    public static final String PARKING_LOT_TOPIC_ROUTING_KEY = "saigonparking.parkinglot";

    private static final Pattern USER_QUEUE_NAME_PATTERN = Pattern.compile("user_(\\d+)_queue");

    public static String generateUserRoutingKey(@NotNull Long userId) {
        return getUserQueueName(userId) + ".#";
    }

    public static String getUserRoutingKey(@NotNull Long userId) {
        return getUserQueueName(userId);
    }

    public static String getUserQueueName(@NotNull Long userId) {
        return String.format("user_%d_queue", userId);
    }

    public static String getParkingLotExchangeName(@NotNull Long parkingLotId) {
        return String.format("parking_lot_%d_exchange", parkingLotId);
    }

    public static Long getUserIdFromUserQueueName(@NotEmpty String userQueueName) {
        Matcher matcher = USER_QUEUE_NAME_PATTERN.matcher(userQueueName);
        if (matcher.find()) {
            String userId = matcher.group(1);
            return Long.valueOf(userId);
        }
        throw new IncorrectQueueNameException();
    }
}