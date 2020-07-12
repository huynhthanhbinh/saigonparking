package com.bht.saigonparking.service.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * This class is booking-service main class
 * which contains the main() method to execute the service.
 * Contact service is simply a spring-boot server
 * which use for communication purposes only
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class BookingService extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BookingService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingService.class, args);
    }
}