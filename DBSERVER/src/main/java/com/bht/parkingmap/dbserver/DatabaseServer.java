package com.bht.parkingmap.dbserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bht.parkingmap.dbserver.configuration.SpringApplicationContext;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotInformationRepository;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotRatingRepository;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotRepository;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotSuggestionRepository;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotTypeRepository;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotUnitRepository;
import com.bht.parkingmap.dbserver.repository.user.CustomerRepository;
import com.bht.parkingmap.dbserver.repository.user.ParkingLotEmployeeRepository;
import com.bht.parkingmap.dbserver.repository.user.UserRepository;
import com.bht.parkingmap.dbserver.repository.user.UserRoleRepository;

/**
 *
 * @author bht
 */
@EnableScheduling
@SpringBootApplication
public class DatabaseServer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DatabaseServer.class);
    }

    @SuppressWarnings({"unused", "java:S1854", "java:S1481", "java:S106"})
    public static void main(String[] args) {
        SpringApplication.run(DatabaseServer.class, args);

        ParkingLotInformationRepository parkingLotInformationRepository = SpringApplicationContext.getBean(ParkingLotInformationRepository.class);
        ParkingLotRatingRepository parkingLotRatingRepository = SpringApplicationContext.getBean(ParkingLotRatingRepository.class);
        ParkingLotRepository parkingLotRepository = SpringApplicationContext.getBean(ParkingLotRepository.class);
        ParkingLotSuggestionRepository parkingLotSuggestionRepository = SpringApplicationContext.getBean(ParkingLotSuggestionRepository.class);
        ParkingLotTypeRepository parkingLotTypeRepository = SpringApplicationContext.getBean(ParkingLotTypeRepository.class);
        ParkingLotUnitRepository parkingLotUnitRepository = SpringApplicationContext.getBean(ParkingLotUnitRepository.class);

        CustomerRepository customerRepository = SpringApplicationContext.getBean(CustomerRepository.class);
        ParkingLotEmployeeRepository parkingLotEmployeeRepository = SpringApplicationContext.getBean(ParkingLotEmployeeRepository.class);
        UserRepository userRepository = SpringApplicationContext.getBean(UserRepository.class);
        UserRoleRepository userRoleRepository = SpringApplicationContext.getBean(UserRoleRepository.class);

        System.out.println(parkingLotTypeRepository.getOne(1L));
        System.out.println(parkingLotRepository.getById(1L));
        System.out.println(parkingLotInformationRepository.getById(1L));
        System.out.println(userRepository.getByUsername("htbinh"));
        System.out.println(userRepository.getById(1L));
    }
}