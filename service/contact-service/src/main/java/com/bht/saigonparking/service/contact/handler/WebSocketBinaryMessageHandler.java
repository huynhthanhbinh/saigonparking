package com.bht.saigonparking.service.contact.handler;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.SYSTEM_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.NOTIFICATION;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.bht.saigonparking.api.grpc.contact.NotificationContent;
import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.service.ContactService;
import com.bht.saigonparking.service.contact.service.MessagingService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor
public final class WebSocketBinaryMessageHandler extends BinaryWebSocketHandler {

    private static final String LOGGING_KEY = "WebSocketBinaryMessageHandler";
    private final WebSocketUserSessionManagement webSocketUserSessionManagement;
    private final ContactService contactService;
    private final MessagingService messagingService;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws IOException {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        webSocketUserSessionManagement.addNewUserSession(userId, session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "connectionEstablishedWithUser", userId.toString());

        NotificationContent notificationContent = NotificationContent.newBuilder()
                .setNotification("Connection to Contact service established !")
                .build();

        SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.newBuilder()
                .setClassification(SYSTEM_MESSAGE)
                .setType(NOTIFICATION)
                .setSenderId(0)
                .setReceiverId(userId)
                .setContent(notificationContent.toByteString())
                .build();

        session.sendMessage(new BinaryMessage(saigonParkingMessage.toByteArray()));
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "connectionClosedFromUser", userId.toString());
        webSocketUserSessionManagement.removeUserSession(userId, session);
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "transportErrorFromSessionOfUser", userId.toString());
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "transportErrorException", exception.getClass().getSimpleName());
    }

    @Override
    protected void handleBinaryMessage(@NonNull WebSocketSession session, @NonNull BinaryMessage message) throws Exception {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handleBinaryMessage", String.format("newBinaryMessageFromUser(%d)", userId));

        SaigonParkingMessage.Builder messageBuilder = SaigonParkingMessage.newBuilder().mergeFrom(message.getPayload().array());

        if (messageBuilder.getReceiverId() != 0) {

            /* receiver's id != 0 --> not send to system --> forward to receiver */
            SaigonParkingMessage.Builder prePublishMessage = messagingService.prePublishMessageToQueue(messageBuilder, session);
            messagingService.publishMessageToQueue(prePublishMessage.build());

        } else {
            /* receiver's id == 0 --> send to system --> not forward to receiver */
            contactService.handleMessageSendToSystem(messageBuilder.build(), session);
        }
    }
}