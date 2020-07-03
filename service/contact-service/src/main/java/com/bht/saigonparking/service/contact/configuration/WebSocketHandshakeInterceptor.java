package com.bht.saigonparking.service.contact.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 *
 * @author bht
 */
@Component
public final class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    public static final String SAIGON_PARKING_USER_KEY = "saigon_parking_user";
    private static final String USER_AUTHORIZATION_KEY = "Authorization";

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest httpRequest,
                                   @NonNull ServerHttpResponse httpResponse,
                                   @NonNull WebSocketHandler webSocketHandler,
                                   @NonNull Map<String, Object> attributes) throws Exception {

        List<String> authorizationHeaders = httpRequest.getHeaders().get(USER_AUTHORIZATION_KEY);
        if (authorizationHeaders == null || authorizationHeaders.isEmpty()) {
            httpResponse.setStatusCode(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
            return false;
        }

        String accessToken = authorizationHeaders.get(0);
        System.out.println(accessToken);

        attributes.put(SAIGON_PARKING_USER_KEY, 1653006L);

        return super.beforeHandshake(httpRequest, httpResponse, webSocketHandler, attributes);
    }
}