package com.bht.saigonparking.service.contact.service;

import javax.validation.constraints.NotNull;

import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;

/**
 *
 * @author bht
 */
public interface MessagingService {

    SaigonParkingMessage.Builder prePublishMessageToQueue(@NotNull SaigonParkingMessage.Builder delegate,
                                                          @NotNull WebSocketSession webSocketSession);

    void publishMessageToQueue(@NotNull SaigonParkingMessage saigonParkingMessage);

    void consumeMessageFromQueue(@NotNull SaigonParkingMessage saigonParkingMessage, @NotNull Long receiverUserId);
}