package com.bht.saigonparking.service.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * This class is auth-service main class
 * which contains the main() method to execute the service.
 * Auth service is simply a spring-boot server
 * which communicate indirectly with the RDBMS through User Service
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class AuthService extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AuthService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthService.class, args);
    }
}