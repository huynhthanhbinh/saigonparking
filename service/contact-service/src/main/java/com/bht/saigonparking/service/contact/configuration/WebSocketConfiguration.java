package com.bht.saigonparking.service.contact.configuration;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.bht.saigonparking.service.contact.handler.WebSocketBinaryMessageHandler;
import com.bht.saigonparking.service.contact.handler.WebSocketTextMessageHandler;
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

    private final WebSocketTextMessageHandler webSocketTextMessageHandler;
    private final WebSocketBinaryMessageHandler webSocketBinaryMessageHandler;

    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final WebSocketHandshakeWebInterceptor webSocketHandshakeWebInterceptor;

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry
                .addHandler(webSocketBinaryMessageHandler, "/") /* correspondent to /contact */
                .addInterceptors(webSocketHandshakeInterceptor)
                .setAllowedOrigins("*");

        webSocketHandlerRegistry
                .addHandler(webSocketBinaryMessageHandler, "/*") /* correspondent to /contact/web?token=... */
                .addInterceptors(webSocketHandshakeWebInterceptor)
                .setAllowedOrigins("*");

        webSocketHandlerRegistry
                .addHandler(webSocketTextMessageHandler, "/text") /* correspondent to /contact/text */
                .addInterceptors(webSocketHandshakeInterceptor)
                .setAllowedOrigins("*");

        webSocketHandlerRegistry
                .addHandler(webSocketTextMessageHandler, "/text/*") /* correspondent to /contact/text/web?token=... */
                .addInterceptors(webSocketHandshakeWebInterceptor)
                .setAllowedOrigins("*");
    }
}