package com.bht.saigonparking.service.contact.handler;

import static com.bht.saigonparking.service.contact.configuration.WebSocketConfiguration.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.bht.saigonparking.common.constant.SaigonParkingMessageQueue;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy)) /* deal with circular dependencies injection */
public class WebSocketUserSessionManagement {

    private final Map<Long, Set<WebSocketSession>> userSessionMap = new HashMap<>(); /* is a map of <userId, session> */
    private final AbstractMessageListenerContainer messageListenerContainer;

    @Async
    public void addNewUserSession(@NonNull Long userId, @NonNull WebSocketSession webSocketSession) {

        if (userSessionMap.containsKey(userId)) {
            userSessionMap.get(userId).add(webSocketSession);

        } else {

            Set<WebSocketSession> sessionSet = new HashSet<>();
            sessionSet.add(webSocketSession);
            userSessionMap.put(userId, sessionSet);
        }
    }

    @Async
    public void removeUserSession(@NonNull Long userId, @NonNull WebSocketSession webSocketSession) {

        if (userSessionMap.containsKey(userId)) {
            boolean isCurrentSessionAuxiliary = getUserAuxiliaryFromSession(webSocketSession);
            Set<WebSocketSession> sessionSet = userSessionMap.get(userId);
            sessionSet.remove(webSocketSession);

            if (sessionSet.isEmpty()) {
                userSessionMap.remove(userId);
                if (!isCurrentSessionAuxiliary) {
                    messageListenerContainer.removeQueueNames(SaigonParkingMessageQueue.getUserQueueName(userId));
                }
            } else {
                Set<Boolean> isSessionAuxiliarySet = sessionSet.stream().map(this::getUserAuxiliaryFromSession).collect(Collectors.toSet());
                if (!isCurrentSessionAuxiliary && isSessionAuxiliarySet.size() == 1 && isSessionAuxiliarySet.contains(Boolean.TRUE)) {
                    messageListenerContainer.removeQueueNames(SaigonParkingMessageQueue.getUserQueueName(userId));
                }
            }
        }
    }

    public Set<WebSocketSession> getAllSessionOfUser(@NonNull Long userId) {
        return userSessionMap.get(userId);
    }

    public Long getUserIdFromSession(@NonNull WebSocketSession webSocketSession) {
        return (Long) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_ID_KEY);
    }

    public String getUserRoleFromSession(@NonNull WebSocketSession webSocketSession) {
        return (String) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_ROLE_KEY);
    }

    public boolean getUserAuxiliaryFromSession(@NonNull WebSocketSession webSocketSession) {
        return (boolean) webSocketSession.getAttributes().get(SAIGON_PARKING_USER_AUXILIARY_KEY);
    }

    public Long getParkingLotIdFromSession(@NonNull WebSocketSession webSocketSession) {
        return (Long) webSocketSession.getAttributes().get(SAIGON_PARKING_PARKING_LOT_ID_KEY);
    }
}