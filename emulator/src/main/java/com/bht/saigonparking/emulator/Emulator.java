package com.bht.saigonparking.emulator;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bht.saigonparking.emulator.configuration.SpringApplicationContext;
import com.bht.saigonparking.emulator.handler.WebSocketHandler;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author bht
 */
@Log4j2
@EnableScheduling
@SpringBootApplication
@SuppressWarnings("all")
public class Emulator extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Emulator.class);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        SpringApplication.run(Emulator.class, args);
        runTest();
    }

    private static void runTest() throws ExecutionException, InterruptedException, IOException {
        TextWebSocketHandler webSocketHandler = SpringApplicationContext.getBean(WebSocketHandler.class);
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.put("Authorization", Collections.singletonList("Bearer htbinh.305.1998"));

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketSession webSocketSession = webSocketClient
                .doHandshake(webSocketHandler, webSocketHttpHeaders, URI.create("ws://localhost:8000/contact")).get();

        webSocketSession.sendMessage(new TextMessage("Nice to meet you"));
    }
}