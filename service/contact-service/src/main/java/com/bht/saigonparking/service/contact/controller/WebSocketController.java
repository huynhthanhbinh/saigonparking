package com.bht.saigonparking.service.contact.controller;

import org.apache.logging.log4j.Level;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.bht.saigonparking.common.util.LoggingUtil;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Controller
@RequiredArgsConstructor
public final class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send/message")
    public void sendMessage(String message) {
        LoggingUtil.log(Level.INFO, "WebSocketController", "sendMessage", message);
        messagingTemplate.convertAndSend("/message", message);
    }
}