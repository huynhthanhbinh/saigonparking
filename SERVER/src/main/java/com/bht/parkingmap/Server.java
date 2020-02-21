package com.bht.parkingmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bht.parkingmap.configuration.SpringApplicationContext;
import com.bht.parkingmap.service.parkinglot.ParkingLotInformationService;
import com.bht.parkingmap.service.parkinglot.ParkingLotService;
import com.google.type.LatLng;

import lombok.extern.log4j.Log4j;

/**
 *
 * @author bht
 */
@Log4j
@SuppressWarnings({"squid:S1854", "squid:S1481", "squid:S106", "squid:CommentedOutCodeLine"})
@SpringBootApplication
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);

        ParkingLotService parkingLotService = SpringApplicationContext.getBean(ParkingLotService.class);
        ParkingLotInformationService parkingLotInformationService = SpringApplicationContext.getBean(ParkingLotInformationService.class);

        LatLng northEast = LatLng.newBuilder().setLatitude(10.895321394865642).setLongitude(106.78215131163597).build();
        LatLng southWest = LatLng.newBuilder().setLatitude(10.672923027175684).setLongitude(106.64089847356081).build();
        LatLng centerPnt = LatLng.newBuilder().setLatitude(10.784122211291663).setLongitude(106.71152489259839).build();

        System.out.println(parkingLotService.getParkingLotById(1L));
        System.out.println("\n\n\n");

        System.out.println(parkingLotInformationService.getParkingLotInformationById(1L));
        System.out.println("\n\n\n");

        parkingLotService.getAllParkingLotCurrentlyWorkingInRegion(northEast, southWest).forEach(System.out::println);
        System.out.println("\n\n\n");

        parkingLotService.getAllParkingLotCurrentlyWorkingInRegionOfRadius(centerPnt, (short) 10)
                .forEach(tuple -> {
                    for (int i = 0; i < 9; i++) {
                        System.out.print(tuple.get(i) + ", ");
                    }
                    System.out.println();
                });
        log.info("Fin");
    }
}