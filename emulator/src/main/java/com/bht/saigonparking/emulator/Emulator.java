package com.bht.saigonparking.emulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotInformation;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.emulator.configuration.SpringApplicationContext;
import com.google.protobuf.Int64Value;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author bht
 */
@Log4j2
@EnableScheduling
@SpringBootApplication
public class Emulator extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Emulator.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Emulator.class, args);
        runTest();
    }

    private static void runTest() {
        ParkingLotServiceGrpc.ParkingLotServiceBlockingStub parkingLotServiceBlockingStub = SpringApplicationContext
                .getBean(ParkingLotServiceGrpc.ParkingLotServiceBlockingStub.class);
        UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub = SpringApplicationContext
                .getBean(UserServiceGrpc.UserServiceBlockingStub.class);

        ParkingLotInformation parkingLotInformation;
        User user;

        log.info("Start calling API");
        parkingLotInformation = parkingLotServiceBlockingStub.getParkingLotById(Int64Value.of(1)).getInformation();
        log.info("End calling API");
        log.info("\n" + parkingLotInformation);

        log.info("Start calling API");
        user = userServiceBlockingStub.getUserById(Int64Value.of(1));
        log.info("End calling API");
        log.info("\n" + user);

        log.info("Start calling API");
        parkingLotInformation = parkingLotServiceBlockingStub.getParkingLotById(Int64Value.of(3)).getInformation();
        log.info("End calling API");
        log.info("\n" + parkingLotInformation);

        log.info("Start calling API");
        user = userServiceBlockingStub.getUserById(Int64Value.of(4));
        log.info("End calling API");
        log.info("\n" + user);
    }
}