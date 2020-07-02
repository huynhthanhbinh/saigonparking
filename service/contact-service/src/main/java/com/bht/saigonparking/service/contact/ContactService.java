package com.bht.saigonparking.service.contact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * This class is contact-service main class
 * which contains the main() method to execute the service.
 * Contact service is simply a spring-boot server
 * which use for communication purposes only
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class ContactService extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ContactService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ContactService.class, args);
    }
}