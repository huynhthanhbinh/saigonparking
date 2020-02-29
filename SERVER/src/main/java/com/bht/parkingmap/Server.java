package com.bht.parkingmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class Server extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Server.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}