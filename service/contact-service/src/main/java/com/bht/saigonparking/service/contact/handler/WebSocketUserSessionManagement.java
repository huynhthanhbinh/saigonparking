package com.bht.saigonparking.service.contact.handler;

import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_KEY;
import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_ROLE_KEY;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private final Map<Long, Set<WebSocketSession>> userSessionMap = new HashMap<>(); /* is a map of <userId, session> */

    public void addNewUserSession(@NonNull Long userId, @NonNull WebSocketSession webSocketSession) {

        if (userSessionMap.containsKey(userId)) {
            userSessionMap.get(userId).add(webSocketSession);

        } else {
            userSessionMap.put(userId, Collections.singleton(webSocketSession));
        }
    }

    public void removeUserSession(@NonNull Long userId, @NonNull WebSocketSession webSocketSession) {

        if (userSessionMap.containsKey(userId)) {
            Set<WebSocketSession> sessionSet = userSessionMap.get(userId);
            sessionSet.remove(webSocketSession);

            if (sessionSet.isEmpty()) {
                userSessionMap.remove(userId);
            }
        }
    }

    public Set<WebSocketSession> getAllSessionOfUser(@NonNull Long userId) {
        return userSessionMap.get(userId);
    }

    public Long getUserIdFromSession(@NonNull WebSocketSession webSocketSession) {
        return (Long) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_KEY);
    }

    public String getUserRoleFromSession(@NonNull WebSocketSession webSocketSession) {
        return (String) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_ROLE_KEY);
    }
}