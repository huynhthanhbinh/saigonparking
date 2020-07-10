package com.bht.saigonparking.service.contact.handler;

import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_KEY;
import static com.bht.saigonparking.service.contact.interceptor.WebSocketInterceptorConstraint.SAIGON_PARKING_USER_ROLE_KEY;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.service.contact.service.QueueService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy)) /* deal with circular dependencies injection */
public final class WebSocketUserSessionManagement {

    private final Map<Long, Set<WebSocketSession>> userSessionMap = new HashMap<>(); /* is a map of <userId, session> */
    private final QueueService queueService;

    public void addNewUserSession(@NonNull Long userId, @NonNull WebSocketSession webSocketSession) {

        if (userSessionMap.containsKey(userId)) {
            userSessionMap.get(userId).add(webSocketSession);

        } else {

            Set<WebSocketSession> sessionSet = new HashSet<>();
            sessionSet.add(webSocketSession);
            userSessionMap.put(userId, sessionSet);
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