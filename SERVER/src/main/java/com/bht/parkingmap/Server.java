package com.bht.parkingmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bht.parkingmap.configuration.SpringApplicationContext;
import com.bht.parkingmap.service.parkinglot.ParkingLotInformationService;
import com.bht.parkingmap.service.parkinglot.ParkingLotService;
import com.google.type.LatLng;

/**
 *
 * @author bht
 */
@SuppressWarnings({"squid:S1854", "squid:S1481", "squid:S106", "squid:CommentedOutCodeLine"})
@SpringBootApplication
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);

        ParkingLotService parkingLotService = SpringApplicationContext.getBean(ParkingLotService.class);
        ParkingLotInformationService parkingLotInformationService = SpringApplicationContext.getBean(ParkingLotInformationService.class);

        System.out.println(parkingLotService.getParkingLotById(1L));
        System.out.println(parkingLotInformationService.getParkingLotInformationById(1L));

        LatLng northEast = LatLng.newBuilder().setLatitude(10.895321394865642).setLongitude(106.78215131163597).build();
        LatLng southWest = LatLng.newBuilder().setLatitude(10.672923027175684).setLongitude(106.64089847356081).build();

        parkingLotService.getAllParkingLotCurrentlyWorkingInRegion(northEast, southWest).forEach(System.out::println);
    }
}