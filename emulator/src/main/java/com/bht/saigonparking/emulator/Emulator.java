package com.bht.saigonparking.emulator;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.CUSTOMER_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.AVAILABILITY_UPDATE;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bht.saigonparking.api.grpc.contact.AvailabilityUpdateContent;
import com.bht.saigonparking.api.grpc.contact.NotificationContent;
import com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage;
import com.bht.saigonparking.api.grpc.contact.TextMessageContent;
import com.bht.saigonparking.emulator.configuration.SpringApplicationContext;
import com.bht.saigonparking.emulator.handler.WebSocketHandler;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

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

    private static final String SAMPLE_TOKEN_CUSTOMER = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIyOTIwMzczOTE3OTY1NDQ5Mzg4QDE1OTM3ODQwNzY4MjYiLCJpc3MiOiJ3d3cuc2FpZ29ucGFya2luZy53dGYiLCJyb2xlIjoiQ1VTVE9NRVIiLCJmYWMiOjE1OCwiY2xhc3NpZmljYXRpb24iOiJBQ0NFU1NfVE9LRU4iLCJzdWIiOiIyOTIwMzczOTE3OTY1NDQ5Mzg4IiwiaWF0IjoxNTkzNzg0MDc2LCJleHAiOjIzMTk1NDQwNzZ9.Hf0iUOFva2ToExAZXi6jcIzMpWBbideNGOnzHLtpu1uJs3J9HQF8CZrxKKMCbK0rQ-5j3yg_Ovm8flqMSXZRnA";
    private static final String SAMPLE_TOKEN_EMPLOYEE = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIyNTAzNDU2NTMyNTg4NDQ0ODcyQDE1OTM3ODQyNzc2ODciLCJpc3MiOiJ3d3cuc2FpZ29ucGFya2luZy53dGYiLCJyb2xlIjoiUEFSS0lOR19MT1RfRU1QTE9ZRUUiLCJmYWMiOjgwNSwiY2xhc3NpZmljYXRpb24iOiJBQ0NFU1NfVE9LRU4iLCJzdWIiOiIyNTAzNDU2NTMyNTg4NDQ0ODcyIiwiaWF0IjoxNTkzNzg0Mjc3LCJleHAiOjIzMTk1NDQyNzd9.s2uE7fHIiAhjgsO5Oz9Gl4fIOXdC2Va8NjHS06x9q5V7laBCdOHF9CyaCaP34fZdLGxRj4xxqGWOS4hJIGDrPg";

    private static final URI WEB_SOCKET_LOCAL_URI = URI.create("ws://localhost:8000/contact");
    private static final URI WEB_SOCKET_WEB_LOCAL_URI = URI.create("ws://localhost:8000/contact/web?token=" + SAMPLE_TOKEN_CUSTOMER);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Emulator.class);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, WebSocketException {
        SpringApplication.run(Emulator.class, args);
        runTest();
    }

    private static void runTest() throws ExecutionException, InterruptedException, IOException, WebSocketException {
//        testAuthWithWebSocketUri();
//        testAuthWithWebSocketWebUri();
        testNewSocketLibrary();
    }

    private static void testAuthWithWebSocketUri() throws ExecutionException, InterruptedException, IOException {
        TextWebSocketHandler webSocketHandler = SpringApplicationContext.getBean(WebSocketHandler.class);
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.put("Authorization", Collections.singletonList(SAMPLE_TOKEN_CUSTOMER));

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketSession webSocketSession = webSocketClient
                .doHandshake(webSocketHandler, webSocketHttpHeaders, WEB_SOCKET_LOCAL_URI).get();

        webSocketSession.close();
    }

    private static void testAuthWithWebSocketWebUri() throws ExecutionException, InterruptedException, IOException {
        TextWebSocketHandler webSocketHandler = SpringApplicationContext.getBean(WebSocketHandler.class);
        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketSession webSocketSession = webSocketClient
                .doHandshake(webSocketHandler, webSocketHttpHeaders, WEB_SOCKET_WEB_LOCAL_URI).get();

        webSocketSession.close();
    }

    private static void testNewSocketLibrary() throws IOException, WebSocketException, InterruptedException {
        WebSocketFactory webSocketFactory = new WebSocketFactory();
        WebSocket webSocket = webSocketFactory.createSocket(WEB_SOCKET_LOCAL_URI, 86400000);
        webSocket.addHeader("Authorization", SAMPLE_TOKEN_EMPLOYEE);
        webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
                SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.parseFrom(binary);
                System.out.println(saigonParkingMessage);

                NotificationContent notificationContent = NotificationContent.parseFrom(saigonParkingMessage.getContent());
                System.out.println(notificationContent);
            }
        });
        webSocket.connect();

        TextMessageContent textMessageContent = TextMessageContent.newBuilder()
                .setSender("htbinh")
                .setMessage("Hello Vu Hai")
                .build();

        AvailabilityUpdateContent availabilityUpdateContent = AvailabilityUpdateContent.newBuilder()
                .setParkingLotId(1)
                .setNewAvailability(27)
                .build();

        SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.newBuilder()
                .setClassification(CUSTOMER_MESSAGE)
                .setType(AVAILABILITY_UPDATE)
                .setSenderId(84)
                .setReceiverId(0)
                .setContent(availabilityUpdateContent.toByteString())
                .build();

        webSocket.sendBinary(saigonParkingMessage.toByteArray());

        Thread.sleep(10000);
        webSocket.disconnect();
    }
}