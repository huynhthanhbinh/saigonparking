package com.bht.saigonparking.service.contact.service.impl;

import java.io.IOException;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.Level;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.api.grpc.contact.BookingAcceptanceContent;
import com.bht.saigonparking.api.grpc.contact.BookingCancellationContent;
import com.bht.saigonparking.api.grpc.contact.BookingFinishContent;
import com.bht.saigonparking.api.grpc.contact.BookingRejectContent;
import com.bht.saigonparking.api.grpc.contact.BookingRequestContent;
import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.common.constant.SaigonParkingMessageQueue;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.handler.WebSocketUserSessionManagement;
import com.bht.saigonparking.service.contact.service.MessagingService;
import com.google.protobuf.InvalidProtocolBufferException;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class MessagingServiceImpl implements MessagingService {

    private final RabbitTemplate rabbitTemplate;
    private final WebSocketUserSessionManagement webSocketUserSessionManagement;

    @Override
    public SaigonParkingMessage.Builder prePublishMessageToQueue(@NotNull SaigonParkingMessage.Builder delegate,
                                                                 @NotNull WebSocketSession webSocketSession) throws InvalidProtocolBufferException {
        switch (delegate.getClassification()) {
            case CUSTOMER_MESSAGE:
                delegate.setSenderId(webSocketUserSessionManagement.getUserIdFromSession(webSocketSession));
                break;
            case PARKING_LOT_MESSAGE:
                delegate.setSenderId(webSocketUserSessionManagement.getParkingLotIdFromSession(webSocketSession));
                break;
            default:
                break;
        }
        return preProcessingMessage(delegate, webSocketSession);
    }

    @Override
    public void publishMessageToQueue(@NotNull SaigonParkingMessage saigonParkingMessage) {
        switch (saigonParkingMessage.getClassification()) {
            case PARKING_LOT_MESSAGE:
                forwardMessageToCustomer(saigonParkingMessage);
                return;
            case CUSTOMER_MESSAGE:
                forwardMessageToParkingLot(saigonParkingMessage);
                return;
            default:
                break;
        }
    }

    @Override
    public void consumeMessageFromQueue(@NotNull SaigonParkingMessage saigonParkingMessage, @NotNull Long receiverUserId) {
        Set<WebSocketSession> userSessionSet = webSocketUserSessionManagement.getAllSessionOfUser(receiverUserId);

        if (userSessionSet != null) {
            userSessionSet.forEach(userSession -> {
                try {
                    userSession.sendMessage(new BinaryMessage(saigonParkingMessage.toByteArray()));

                } catch (IOException e) {
                    LoggingUtil.log(Level.ERROR, String.format("forwardMessageToReceiver(%d)", receiverUserId),
                            "Exception", e.getMessage());
                }
            });
        }
        LoggingUtil.log(Level.ERROR, "SERVICE", String.format("forwardMessageToReceiver(%d)", receiverUserId),
                String.format("nSessionOfReceiver: %d", (userSessionSet != null) ? userSessionSet.size() : 0));
    }

    private void forwardMessageToCustomer(@NotNull SaigonParkingMessage message) {
        try {
            String routingKey = SaigonParkingMessageQueue.getUserRoutingKey(message.getReceiverId());
            rabbitTemplate.convertAndSend(routingKey, message);

        } catch (Exception exception) {
            LoggingUtil.log(Level.ERROR, "forwardMessageToCustomer", "Exception", exception.getClass().getSimpleName());
        }
    }

    private void forwardMessageToParkingLot(@NotNull SaigonParkingMessage message) {
        try {
            String exchangeName = SaigonParkingMessageQueue.getParkingLotExchangeName(message.getReceiverId());
            rabbitTemplate.convertAndSend(exchangeName, "", message);

        } catch (Exception exception) {
            LoggingUtil.log(Level.ERROR, "forwardMessageToParkingLot", "Exception", exception.getClass().getSimpleName());
        }
    }

    private SaigonParkingMessage.Builder preProcessingMessage(@NotNull SaigonParkingMessage.Builder delegate,
                                                              @NotNull WebSocketSession webSocketSession) throws InvalidProtocolBufferException {
        switch (delegate.getType()) {
            case BOOKING_REQUEST:
                BookingRequestContent bookingRequestContent = BookingRequestContent.parseFrom(delegate.getContent());
                break;
            case BOOKING_CANCELLATION:
                BookingCancellationContent bookingCancellationContent = BookingCancellationContent.parseFrom(delegate.getContent());
                break;
            case BOOKING_ACCEPTANCE:
                BookingAcceptanceContent bookingAcceptanceContent = BookingAcceptanceContent.parseFrom(delegate.getContent());
                break;
            case BOOKING_REJECT:
                BookingRejectContent bookingRejectContent = BookingRejectContent.parseFrom(delegate.getContent());
                break;
            case BOOKING_FINISH:
                BookingFinishContent bookingFinishContent = BookingFinishContent.parseFrom(delegate.getContent());
                break;
            default:
                break;
        }
        return delegate;
    }
}