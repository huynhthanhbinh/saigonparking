package com.bht.parkingmap;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bht.parkingmap.api.parkinglot.ParkingLot;
import com.bht.parkingmap.configuration.SpringApplicationContext;
import com.bht.parkingmap.mapper.parkinglot.ParkingLotMapper;
import com.bht.parkingmap.service.parkinglot.ParkingLotService;

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

        ParkingLotMapper parkingLotMapper = SpringApplicationContext.getBean(ParkingLotMapper.class);
        ParkingLotService parkingLotService = SpringApplicationContext.getBean(ParkingLotService.class);

        log.info("Test parking-lot list");
        List<ParkingLot> parkingLotList = parkingLotMapper.toParkingLotList(parkingLotService
                .getAllParkingLotCurrentlyWorkingInRegion(
                        10.895321394865642, 106.78215131163597,
                        10.672923027175684, 106.64089847356081));

        log.info("Test finished ! Try to print out !");
        parkingLotList.forEach(System.out::println);

        System.out.println("\n\n\n");

        log.info("Test parking-lot list with distance");
        List<ParkingLot> parkingLotListWithDistance = parkingLotService
                .getAllParkingLotCurrentlyWorkingInRegionOfRadius(10.784122211291663, 106.71152489259839, 2);

        log.info("Test finished ! Try to print out !");
        parkingLotListWithDistance.forEach(System.out::println);
    }
}