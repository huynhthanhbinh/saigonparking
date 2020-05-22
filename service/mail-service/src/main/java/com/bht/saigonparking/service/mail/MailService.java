package com.bht.saigonparking.service.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * This class is mail-service main class
 * which contains the main() method to execute the service.
 * Auth service is simply a spring-boot server
 * which aim to send non-reply email to client
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class MailService extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MailService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MailService.class, args);
    }
}