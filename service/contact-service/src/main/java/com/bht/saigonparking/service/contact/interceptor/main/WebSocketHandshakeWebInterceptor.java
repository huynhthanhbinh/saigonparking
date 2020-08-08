package com.bht.saigonparking.service.contact.interceptor.main;

import static com.bht.saigonparking.service.contact.configuration.WebSocketConfiguration.WEB_AUTH_PATH_PREFIX;
import static com.bht.saigonparking.service.contact.configuration.WebSocketConfiguration.WEB_AUTH_PATH_PREFIX_LENGTH;

import java.net.URI;
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
public final class WebSocketHandshakeWebInterceptor extends BaseWebSocketHandshakeInterceptor {

    private static final boolean MUST_CONSUME_MESSAGE_FROM_QUEUE = true;

    private final SaigonParkingAuthentication authentication;
    private final HandshakeService handshakeService;

    @Override
    protected SaigonParkingAuthentication getAuthentication() {
        return authentication;
    }

    @Override
    protected String getAccessTokenFromHttpRequest(@NonNull ServerHttpRequest httpRequest) {
        String accessToken = getAccessTokenFromUri(httpRequest.getURI());
        if (accessToken.isEmpty()) {
            throw new MissingTokenException();
        }
        return accessToken;
    }

    @Override
    protected void postAuthentication(@NonNull SaigonParkingTokenBody saigonParkingTokenBody,
                                      @NonNull Map<String, Object> webSocketSessionAttributes) {

        webSocketSessionAttributes.putAll(handshakeService.postAuthentication(saigonParkingTokenBody, MUST_CONSUME_MESSAGE_FROM_QUEUE));
    }

    private String getAccessTokenFromUri(@NonNull URI uriWithAccessToken) {
        String uriString = uriWithAccessToken.toString();
        return uriString.substring(uriString.lastIndexOf(WEB_AUTH_PATH_PREFIX) + WEB_AUTH_PATH_PREFIX_LENGTH);
    }
}