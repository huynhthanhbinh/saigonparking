package com.bht.saigonparking.service.contact.handler;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.SYSTEM_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.ERROR;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.NOTIFICATION;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.bht.saigonparking.api.grpc.contact.ErrorContent;
import com.bht.saigonparking.api.grpc.contact.NotificationContent;
import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.common.util.LoggingUtil;
import com.bht.saigonparking.service.contact.service.ContactService;
import com.bht.saigonparking.service.contact.service.MessagingService;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor
public class WebSocketBinaryMessageHandler extends BinaryWebSocketHandler {

    private static final String LOGGING_KEY = "WebSocketBinaryMessageHandler";
    private final WebSocketUserSessionManagement webSocketUserSessionManagement;
    private final ContactService contactService;
    private final MessagingService messagingService;

    @Async
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

    @Async
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

    @Async
    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    protected void handleBinaryMessage(@NonNull WebSocketSession session, @NonNull BinaryMessage message) throws Exception {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handleBinaryMessage", String.format("newBinaryMessageFromUser(%d)", userId));

        SaigonParkingMessage.Builder messageBuilder = SaigonParkingMessage.newBuilder()
                .mergeFrom(message.getPayload().array());

        try {
            if (messageBuilder.getReceiverId() != 0) {

                /* receiver's id != 0 --> not send to system --> forward to receiver */
                messagingService.prePublishMessageToQueue(messageBuilder, session);
                messagingService.publishMessageToQueue(messageBuilder.build());

            } else {
                /* receiver's id == 0 --> send to system --> not forward to receiver */
                contactService.handleMessageSendToSystem(messageBuilder.build(), session);
            }
        } catch (StatusRuntimeException exception) {

            ErrorContent content = ErrorContent.newBuilder()
                    .setInternalErrorCode(exception.getStatus().getDescription())
                    .build();

            session.sendMessage(new BinaryMessage(messageBuilder
                    .setSenderId(0)
                    .setReceiverId(userId)
                    .setClassification(SYSTEM_MESSAGE)
                    .setType(ERROR)
                    .setContent(content.toByteString())
                    .build()
                    .toByteArray()));
        }
    }
}