package com.bht.saigonparking.service.contact.service;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;

/**
 *
 * @author bht
 */
public interface ContactService {

    void publishMessageToQueue(@NotNull SaigonParkingMessage saigonParkingMessage);

    void consumeMessageFromQueue(@NotNull SaigonParkingMessage saigonParkingMessage);

    void handleMessageSendToSystem(@NotNull SaigonParkingMessage saigonParkingMessage);
}