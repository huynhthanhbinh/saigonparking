package com.bht.saigonparking.service.contact.service;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 *
 * @author bht
 */
public interface IntermediateService {

    void handleBookingRequest(@NotNull SaigonParkingMessage.Builder message,
                              @NotNull WebSocketSession webSocketSession) throws IOException;

    void handleBookingCancellation(@NotNull SaigonParkingMessage.Builder message,
                                   @NotNull MessagingService messagingService) throws InvalidProtocolBufferException;

    void handleBookingAcceptance(@NotNull SaigonParkingMessage.Builder message,
                                 @NotNull MessagingService messagingService) throws InvalidProtocolBufferException;

    void handleBookingReject(@NotNull SaigonParkingMessage.Builder message,
                             @NotNull MessagingService messagingService) throws InvalidProtocolBufferException;
}