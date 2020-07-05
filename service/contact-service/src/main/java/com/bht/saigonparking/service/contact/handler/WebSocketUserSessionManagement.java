package com.bht.saigonparking.service.contact.handler;

import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_KEY;
import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_ROLE_KEY;

import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;

/**
 *
 * @author bht
 */
@Component
public final class WebSocketUserSessionManagement {

    @Getter
    private final Map<Long, WebSocketSession> userSessionMap = new HashMap<>(); /* is a map of <userId, session> */

    public WebSocketSession getSessionOfUser(@NonNull Long userId) {
        return userSessionMap.get(userId);
    }

    public Long getUserIdFromSession(@NonNull WebSocketSession webSocketSession) {
        return (Long) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_KEY);
    }

    public String getUserRoleFromSession(@NonNull WebSocketSession webSocketSession) {
        return (String) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_ROLE_KEY);
    }
}