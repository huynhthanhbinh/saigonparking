package com.bht.saigonparking.service.contact.handler;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bht.saigonparking.common.util.LoggingUtil;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor
public final class WebSocketTextMessageHandler extends TextWebSocketHandler {

    private static final String LOGGING_KEY = "WebSocketTextMessageHandler";
    private final WebSocketUserSessionManagement webSocketUserSessionManagement;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws IOException {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        webSocketUserSessionManagement.addNewUserSession(userId, session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "connectionEstablishedWithUser", userId.toString());
        session.sendMessage(new TextMessage("{ \"notification\":\"Connection to Contact service established !\" }"));
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
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        Long userId = webSocketUserSessionManagement.getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handleTextMessage", String.format("newTextMessageFromUser(%d)", userId));
    }
}