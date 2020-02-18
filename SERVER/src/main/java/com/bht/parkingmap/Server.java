package com.bht.parkingmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bht.parkingmap.configuration.SpringApplicationContext;
import com.bht.parkingmap.entity.user.CustomerEntity;
import com.bht.parkingmap.repository.parkinglot.ParkingLotInformationRepository;
import com.bht.parkingmap.repository.parkinglot.ParkingLotRatingRepository;
import com.bht.parkingmap.repository.parkinglot.ParkingLotRepository;
import com.bht.parkingmap.repository.parkinglot.ParkingLotSuggestionRepository;
import com.bht.parkingmap.repository.parkinglot.ParkingLotTypeRepository;
import com.bht.parkingmap.repository.parkinglot.ParkingLotUnitRepository;
import com.bht.parkingmap.repository.report.ParkingLotReportRepository;
import com.bht.parkingmap.repository.report.ReportRepository;
import com.bht.parkingmap.repository.report.ReportTypeRepository;
import com.bht.parkingmap.repository.report.WrongParkingReportRepository;
import com.bht.parkingmap.repository.user.CustomerRepository;
import com.bht.parkingmap.repository.user.ParkingLotEmployeeRepository;
import com.bht.parkingmap.repository.user.UserRepository;
import com.bht.parkingmap.repository.user.UserRoleRepository;

import javassist.NotFoundException;

/**
 *
 * @author bht
 */
@SuppressWarnings({"squid:S1854", "squid:S1481"})
@SpringBootApplication
public class Server {
    public static void main(String[] args) throws NotFoundException {
        SpringApplication.run(Server.class, args);

        // FOR TESTING PURPOSE ONLY: Defining repo =====================================================================
        ParkingLotTypeRepository parkingLotTypeRepository = SpringApplicationContext.getBean(ParkingLotTypeRepository.class);
        ParkingLotRepository parkingLotRepository = SpringApplicationContext.getBean(ParkingLotRepository.class);
        ParkingLotInformationRepository parkingLotInformationRepository = SpringApplicationContext.getBean(ParkingLotInformationRepository.class);
        ParkingLotUnitRepository parkingLotUnitRepository = SpringApplicationContext.getBean(ParkingLotUnitRepository.class);
        ParkingLotRatingRepository parkingLotRatingRepository = SpringApplicationContext.getBean(ParkingLotRatingRepository.class);
        ParkingLotSuggestionRepository parkingLotSuggestionRepository = SpringApplicationContext.getBean(ParkingLotSuggestionRepository.class);

        ReportTypeRepository reportTypeRepository = SpringApplicationContext.getBean(ReportTypeRepository.class);
        ReportRepository reportRepository = SpringApplicationContext.getBean(ReportRepository.class);
        ParkingLotReportRepository parkingLotReportRepository = SpringApplicationContext.getBean(ParkingLotReportRepository.class);
        WrongParkingReportRepository wrongParkingReportRepository = SpringApplicationContext.getBean(WrongParkingReportRepository.class);

        UserRoleRepository userRoleRepository = SpringApplicationContext.getBean(UserRoleRepository.class);
        UserRepository userRepository = SpringApplicationContext.getBean(UserRepository.class);
        CustomerRepository customerRepository = SpringApplicationContext.getBean(CustomerRepository.class);
        ParkingLotEmployeeRepository parkingLotEmployeeRepository = SpringApplicationContext.getBean(ParkingLotEmployeeRepository.class);

        // FOR TESTING PURPOSE ONLY: Using repo ========================================================================

        System.out.println(userRepository.findById(1L));

        CustomerEntity customerEntity = customerRepository.findById(4L)
                .orElseThrow(() -> new NotFoundException("Not found customer entity"));

        System.out.println(customerEntity);

        customerEntity.setPhone("0968036784");
        customerRepository.saveAndFlush(customerEntity);

        customerEntity = customerRepository.findById(4L)
                .orElseThrow(() -> new NotFoundException("Not found customer entity"));

        System.out.println(customerEntity);
    }
}