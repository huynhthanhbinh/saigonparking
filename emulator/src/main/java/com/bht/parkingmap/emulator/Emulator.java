package com.bht.parkingmap.emulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc;
import com.bht.parkingmap.emulator.configuration.SpringApplicationContext;
import com.google.protobuf.Int64Value;

/**
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class Emulator extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Emulator.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Emulator.class, args);

        System.out.println(SpringApplicationContext
                .getBean(ParkingLotServiceGrpc.ParkingLotServiceBlockingStub.class)
                .getParkingLotById(Int64Value.of(1)).getInformation());
    }
}