package com.bht.saigonparking.service.booking.configuration;

import static com.bht.saigonparking.api.grpc.booking.BookingStatisticRequestType.CREATE;
import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.BOOKING_QUEUE_NAME;

import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.booking.BookingStatisticRequest;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.booking.service.main.BookingService;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageQueueConfiguration {

    private final BookingService bookingService;

    @RabbitListener(queues = {BOOKING_QUEUE_NAME})
    public void consumeMessageFromBookingTopic(@NotNull BookingStatisticRequest request) {
        try {
            switch (request.getType()) {
                case CREATE:
                    bookingService.createOneOrManyParkingLotStatistic(new HashSet<>(request.getParkingLotIdList()));
                    LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                            String.format("createStatisticOfParkingLot: %s", request.getParkingLotIdList()));
                    break;
                case DELETE:
                    bookingService.deleteOneOrManyParkingLotStatistic(new HashSet<>(request.getParkingLotIdList()));
                    LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                            String.format("deleteStatisticOfParkingLot: %s", request.getParkingLotIdList()));
                    break;
                default:
                    break;
            }

        } catch (Exception exception) {

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getClass().getSimpleName());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    (request.getType().equals(CREATE))
                            ? String.format("createStatisticOfParkingLot: %s", request.getParkingLotIdList())
                            : String.format("deleteStatisticOfParkingLot: %s", request.getParkingLotIdList()));
        }
    }
}