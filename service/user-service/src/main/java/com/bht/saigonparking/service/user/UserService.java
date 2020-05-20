package com.bht.saigonparking.service.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bht.saigonparking.common.handler.SaigonParkingExceptionHandler;

/**
 *
 * This class is user-service main class
 * which contains the main() method to execute the service.
 * User service is simply a spring-boot server
 * which communicate directly with the RDBMS
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class UserService extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(UserService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserService.class, args);
        Thread.setDefaultUncaughtExceptionHandler(new SaigonParkingExceptionHandler());
    }
}