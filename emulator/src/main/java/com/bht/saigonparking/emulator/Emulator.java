package com.bht.saigonparking.emulator;

import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.CUSTOMER_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Classification.PARKING_LOT_MESSAGE;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.BOOKING_REQUEST;
import static com.bht.saigonparking.api.grpc.contact.SaigonParkingMessage.Type.TEXT_MESSAGE;

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

import com.bht.saigonparking.api.grpc.contact.BookingRequestContent;
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

//        Thread.sleep(5000);
//
//        ContactServiceGrpc.ContactServiceBlockingStub contactServiceBlockingStub =
//                SpringApplicationContext.getBean(ContactServiceGrpc.ContactServiceBlockingStub.class);
//
//        System.out.println(contactServiceBlockingStub.checkUserOnlineByUserId(Int64Value.of(4)).getValue());
//        System.out.println(contactServiceBlockingStub.checkUserOnlineByUserId(Int64Value.of(84)).getValue());
//        System.out.println(contactServiceBlockingStub.checkParkingLotOnlineByParkingLotId(Int64Value.of(72)).getValue());
//
//        Thread.sleep(86400000);

//        System.out.println(SpringApplicationContext.getBean(BookingServiceGrpc.BookingServiceBlockingStub.class)
//                .createBooking(CreateBookingRequest.newBuilder()
//                        .setCustomerId(4)
//                        .setParkingLotId(1)
//                        .setLicensePlate("59H1-762.17")
//                        .build())
//                .getValue());

//        SpringApplicationContext.getBean(BookingServiceGrpc.BookingServiceBlockingStub.class)
//                .updateBookingStatus(UpdateBookingStatusRequest.newBuilder()
//                        .setBookingId(1)
//                        .setStatus(BookingStatus.REJECTED)
//                        .setTimestamp(new Timestamp(System.currentTimeMillis()).toString())
//                        .build());
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
//        testSocketAsEmployee();
        testSocketAsCustomer();
    }

    private static void testSocketAsCustomer() throws IOException, WebSocketException, InterruptedException {
        WebSocketFactory webSocketFactory = new WebSocketFactory();
        WebSocket webSocket = webSocketFactory.createSocket(WEB_SOCKET_LOCAL_URI, 86400000);

        webSocket.addHeader("Authorization", SAMPLE_TOKEN_CUSTOMER);

        webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
                SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.parseFrom(binary);
                System.out.println(saigonParkingMessage);
                System.out.println(BookingRequestContent.parseFrom(saigonParkingMessage.getContent()));
            }
        });

        webSocket.connect();
        Thread.sleep(2000);

        TextMessageContent customerTextMessageContent = TextMessageContent.newBuilder()
                .setSender("htbinh")
                .setMessage("Hello parkinglot")
                .build();

        BookingRequestContent bookingRequestContent = BookingRequestContent.newBuilder()
                .setBookingId(10000)
                .setAmountOfParkingHour(5)
                .setCustomerName("htbinh")
                .setCustomerLicense("54L6-2908")
                .setParkingLotId(72)
                .build();

        SaigonParkingMessage textMessage = SaigonParkingMessage.newBuilder()
                .setClassification(CUSTOMER_MESSAGE)
                .setType(BOOKING_REQUEST)
                .setSenderId(4)
                .setReceiverId(72)
                .setContent(bookingRequestContent.toByteString())
                .build();

        webSocket.sendBinary(textMessage.toByteArray());
        //webSocket.disconnect();
    }

    private static void testSocketAsEmployee() throws IOException, WebSocketException, InterruptedException {
        WebSocketFactory webSocketFactory = new WebSocketFactory();
        WebSocket webSocket = webSocketFactory.createSocket(WEB_SOCKET_LOCAL_URI, 86400000);

        webSocket.addHeader("Authorization", SAMPLE_TOKEN_EMPLOYEE);

        webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
                SaigonParkingMessage saigonParkingMessage = SaigonParkingMessage.parseFrom(binary);
                System.out.println(saigonParkingMessage);
            }
        });

        webSocket.connect();
        Thread.sleep(2000);

        TextMessageContent parkingLotTextMessageContent = TextMessageContent.newBuilder()
                .setSender("parkinglot")
                .setMessage("Hello htbinh")
                .build();

        SaigonParkingMessage textMessage = SaigonParkingMessage.newBuilder()
                .setClassification(PARKING_LOT_MESSAGE)
                .setType(TEXT_MESSAGE)
                .setSenderId(84)
                .setReceiverId(4)
                .setContent(parkingLotTextMessageContent.toByteString())
                .build();

        webSocket.sendBinary(textMessage.toByteArray());
        //webSocket.disconnect();
    }
}