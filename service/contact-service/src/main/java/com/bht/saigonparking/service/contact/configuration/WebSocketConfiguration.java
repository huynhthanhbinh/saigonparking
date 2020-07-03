package com.bht.saigonparking.service.contact.configuration;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.bht.saigonparking.service.contact.handler.WebSocketHandler;
import com.bht.saigonparking.service.contact.interceptor.WebSocketHandshakeInterceptor;
import com.bht.saigonparking.service.contact.interceptor.WebSocketHandshakeWebInterceptor;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor
public final class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final WebSocketHandshakeWebInterceptor webSocketHandshakeWebInterceptor;

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry
                .addHandler(webSocketHandler, "/")
                .addInterceptors(webSocketHandshakeInterceptor)
                .setAllowedOrigins("*");

        webSocketHandlerRegistry
                .addHandler(webSocketHandler, "/*")
                .addInterceptors(webSocketHandshakeWebInterceptor)
                .setAllowedOrigins("*");
    }
}