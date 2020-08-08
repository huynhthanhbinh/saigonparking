package com.bht.saigonparking.service.contact.configuration;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.bht.saigonparking.service.contact.handler.WebSocketBinaryMessageHandler;
import com.bht.saigonparking.service.contact.handler.WebSocketTextMessageHandler;
import com.bht.saigonparking.service.contact.interceptor.WebSocketHandshakeInterceptor;
import com.bht.saigonparking.service.contact.interceptor.WebSocketHandshakeQrScannerInterceptor;
import com.bht.saigonparking.service.contact.interceptor.WebSocketHandshakeWebInterceptor;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@RequiredArgsConstructor
public final class WebSocketConfiguration implements WebSocketConfigurer {

    public static final String SAIGON_PARKING_USER_ID_KEY = "saigon_parking_user_id";
    public static final String SAIGON_PARKING_USER_ROLE_KEY = "saigon_parking_user_role";
    public static final String SAIGON_PARKING_USER_AUXILIARY_KEY = "saigon_parking_user_auxiliary";
    public static final String SAIGON_PARKING_PARKING_LOT_ID_KEY = "saigon_parking_parking_lot_id";

    private final WebSocketTextMessageHandler webSocketTextMessageHandler;
    private final WebSocketBinaryMessageHandler webSocketBinaryMessageHandler;

    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final WebSocketHandshakeWebInterceptor webSocketHandshakeWebInterceptor;
    private final WebSocketHandshakeQrScannerInterceptor webSocketHandshakeQrScannerInterceptor;

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

        webSocketHandlerRegistry
                .addHandler(webSocketBinaryMessageHandler, "/qrscanner") /* correspondent to /contact/qrscanner */
                .addInterceptors(webSocketHandshakeQrScannerInterceptor)
                .setAllowedOrigins("*");

        webSocketHandlerRegistry
                .addHandler(webSocketBinaryMessageHandler, "/qrscanner/*") /* correspondent to /contact/qrscanner?token=... */
                .addInterceptors(webSocketHandshakeQrScannerInterceptor)
                .setAllowedOrigins("*");
    }
}