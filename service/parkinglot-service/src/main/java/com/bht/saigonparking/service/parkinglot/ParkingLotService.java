package com.bht.saigonparking.service.parkinglot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * This class is parking-map-service main class
 * which contains the main() method to execute the service.
 * Parking-map service is simply a spring-boot server
 * which communicate directly with the RDBMS
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class ParkingLotService extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ParkingLotService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotService.class, args);
    }
}