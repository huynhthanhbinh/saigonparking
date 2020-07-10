package com.bht.saigonparking.service.contact.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;

/**
 *
 * @author bht
 */
public interface QueueService {

    void registerAutoDeleteQueueAndExchangeForUser(@NotNull Long userId, @NotEmpty String userRole);

    void publishMessageToQueue(@NotNull SaigonParkingMessage saigonParkingMessage);
}