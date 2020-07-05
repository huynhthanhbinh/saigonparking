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

import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.common.util.LoggingUtil;

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

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws IOException {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        webSocketUserSessionManagement.getUserSessionMap().put(userId, session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "connectionEstablishedWithUser", userId.toString());

        SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.newBuilder()
                .setClassification(SYSTEM_MESSAGE)
                .setType(NOTIFICATION)
                .setSenderId(0)
                .setReceiverId(userId)
                .setContent("Connection to Contact service established !")
                .build();

        session.sendMessage(new BinaryMessage(saigonParkingMessage.toByteArray()));
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "connectionClosedFromUser", userId.toString());
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

        SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.parseFrom(message.getPayload());
        System.out.println(saigonParkingMessage);
    }
}