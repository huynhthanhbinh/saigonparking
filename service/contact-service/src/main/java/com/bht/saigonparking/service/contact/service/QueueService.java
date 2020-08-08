package com.bht.saigonparking.service.contact.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.amqp.core.Queue;

/**
 *
 * @author bht
 */
public interface QueueService {

    Queue registerAutoDeleteQueueForUser(@NotNull Long userId, boolean isAuxiliaryQueue);

    void registerAutoDeleteExchangeForParkingLot(@NotNull Long parkingLotId, @NotNull Queue employeeQueue);

    boolean isExchangeExist(@NotEmpty String exchangeName);

    boolean isQueueExist(@NotEmpty String queueName);
}