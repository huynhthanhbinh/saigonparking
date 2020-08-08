package com.bht.saigonparking.service.contact.interceptor.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.common.auth.SaigonParkingAuthentication;
import com.bht.saigonparking.common.auth.SaigonParkingTokenBody;
import com.bht.saigonparking.common.base.BaseWebSocketHandshakeInterceptor;
import com.bht.saigonparking.common.exception.MissingTokenException;
import com.bht.saigonparking.service.contact.service.HandshakeService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class WebSocketHandshakeInterceptor extends BaseWebSocketHandshakeInterceptor {

    private static final String USER_AUTHORIZATION_KEY = "Authorization";
    private static final boolean MUST_CONSUME_MESSAGE_FROM_QUEUE = true;

    private final HandshakeService handshakeService;
    private final SaigonParkingAuthentication saigonParkingAuthentication;

    @Override
    protected SaigonParkingAuthentication getAuthentication() {
        return saigonParkingAuthentication;
    }

    @Override
    protected String getAccessTokenFromHttpRequest(@NonNull ServerHttpRequest httpRequest) {
        List<String> authorizationHeaders = httpRequest.getHeaders().get(USER_AUTHORIZATION_KEY);
        if (authorizationHeaders == null || authorizationHeaders.isEmpty()) {
            throw new MissingTokenException();
        }
        return authorizationHeaders.get(0);
    }

    @Override
    protected void postAuthentication(@NonNull SaigonParkingTokenBody saigonParkingTokenBody,
                                      @NonNull Map<String, Object> webSocketSessionAttributes) {

        webSocketSessionAttributes.putAll(handshakeService.postAuthentication(saigonParkingTokenBody, MUST_CONSUME_MESSAGE_FROM_QUEUE));
    }
}