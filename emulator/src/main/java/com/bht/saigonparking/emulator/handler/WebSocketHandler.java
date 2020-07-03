package com.bht.saigonparking.emulator.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bht.saigonparking.emulator.util.LoggingUtil;

import lombok.Getter;

/**
 *
 * @author bht
 */
@Component
public final class WebSocketHandler extends TextWebSocketHandler {

    private static final String LOGGING_KEY = "WebSocketHandler";

    @Getter
    private final List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "afterConnectionEstablished", session.getAttributes().toString());
        session.sendMessage(new TextMessage("Hello Server"));
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "afterConnectionClosed", session.getAttributes().toString());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handleTextMessage", message.getPayload());
    }

    @Override
    protected void handlePongMessage(@NonNull WebSocketSession session, @NonNull PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handlePongMessage", message.getPayload().toString());
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        LoggingUtil.log(Level.INFO, LOGGING_KEY, "handleTransportError", session.getAttributes().toString());
    }
}