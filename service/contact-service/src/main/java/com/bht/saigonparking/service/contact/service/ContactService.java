package com.bht.saigonparking.service.contact.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 *
 * @author bht
 */
public interface ContactService {

    void registerAutoDeleteQueueAndExchangeForUser(@NotNull Long userId, @NotEmpty String userRole);

    void publishMessageToQueue(@NotNull SaigonParkingMessage saigonParkingMessage);

    void consumeMessageFromQueue(@NotNull SaigonParkingMessage saigonParkingMessage);

    void handleMessageSendToSystem(@NotNull SaigonParkingMessage saigonParkingMessage,
                                   @NotNull WebSocketSession session,
                                   @NotNull Long userId) throws InvalidProtocolBufferException;
}