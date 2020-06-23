package com.bht.saigonparking.service.user.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.PARKING_LOT_QUEUE_NAME;
import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.USER_QUEUE_NAME;

import org.apache.logging.log4j.Level;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.parkinglot.DeleteParkingLotNotification;
import com.bht.saigonparking.api.grpc.user.UpdateUserLastSignInRequest;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.user.service.main.UserService;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class MessageQueueConfiguration {

    private final UserService userService;

    @RabbitListener(queues = {USER_QUEUE_NAME})
    public void consumeMessageFromUserTopic(UpdateUserLastSignInRequest request) {
        try {
            userService.updateUserLastSignIn(request.getUserId(), request.getTimeInMillis());
            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("updateUserLastSignIn(%d)", request.getUserId()));

        } catch (Exception exception) {

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("updateUserLastSignIn(%d)", request.getUserId()));
        }
    }

    @RabbitListener(queues = {PARKING_LOT_QUEUE_NAME})
    public void consumeMessageFromParkingLotTopic(DeleteParkingLotNotification notification) {
        try {
            userService.deleteMultiUserById(notification.getEmployeeIdList());
            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("deleteParkingLotEmployeesByParkingLotId(%d)", notification.getParkingLotId()));

        } catch (Exception exception) {

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("deleteParkingLotEmployeesByParkingLotId(%d)", notification.getParkingLotId()));
        }
    }
}