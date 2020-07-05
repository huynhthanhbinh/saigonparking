package com.bht.saigonparking.service.contact.handler;

import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_KEY;
import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_ROLE_KEY;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bht.saigonparking.common.util.LoggingUtil;

import lombok.Getter;

/**
 *
 * @author bht
 */
@Component
public final class WebSocketHandler extends TextWebSocketHandler {

    private static final String LOGGING_KEY = "WebSocketHandler";

    @Getter
    private final Map<Long, WebSocketSession> userSessionMap = new HashMap<>(); /* is a map of <userId, session> */

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws IOException {
        Long userId = getUserIdFromSession(session);
        userSessionMap.put(userId, session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "connectionEstablishedWithUser", userId.toString());
        session.sendMessage(new TextMessage("{ \"message\":\"Connection to Contact service established !\" }"));
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        Long userId = getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "connectionClosedFromUser", userId.toString());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handleTextMessage", message.getPayload());
    }

    @Override
    protected void handlePongMessage(@NonNull WebSocketSession session, @NonNull PongMessage message) {
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handlePongMessage", message.getPayload().toString());
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        Long userId = getUserIdFromSession(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "transportErrorFromSessionOfUser", userId.toString());
    }

    public Long getUserIdFromSession(@NonNull WebSocketSession webSocketSession) {
        return (Long) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_KEY);
    }

    public String getUserRoleFromSession(@NonNull WebSocketSession webSocketSession) {
        return (String) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_ROLE_KEY);
    }
}