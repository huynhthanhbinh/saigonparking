package com.bht.saigonparking.appserver;

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
 * All other upper layer services or web-server...
 * must access the data through Parking-Map Service,
 * cannot call directly to the RDBMS
 * for querying or modifying data !!
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class AppServer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AppServer.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AppServer.class, args);
    }
}