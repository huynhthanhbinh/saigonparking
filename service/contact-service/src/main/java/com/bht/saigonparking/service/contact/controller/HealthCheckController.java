package com.bht.saigonparking.service.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Health Check Controller
 * Using for Consul Service Discovery
 *
 * @author bht
 */
@Controller
public final class HealthCheckController {

    @ResponseBody
    @GetMapping("/actuator/health")
    public String healthCheck() {
        return "OK";
    }
}