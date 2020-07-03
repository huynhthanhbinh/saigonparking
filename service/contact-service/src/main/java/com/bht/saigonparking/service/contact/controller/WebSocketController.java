package com.bht.saigonparking.service.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author bht
 */
@Controller
public final class WebSocketController {

    @ResponseBody
    @GetMapping(value = "/contact", headers = "Connection!=Upgrade")
    public String nonUpgrade() {
        return "Saigon Parking Websocket Controller";
    }
}