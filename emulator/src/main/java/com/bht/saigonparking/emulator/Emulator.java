package com.bht.saigonparking.emulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bht.saigonparking.api.grpc.auth.AuthServiceGrpc;
import com.bht.saigonparking.api.grpc.auth.ValidateResponse;
import com.bht.saigonparking.api.grpc.parkinglot.CountAllParkingLotHasRatingsRequest;
import com.bht.saigonparking.api.grpc.parkinglot.CountAllRatingsOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.parkinglot.GetAllParkingLotHasRatingsRequest;
import com.bht.saigonparking.api.grpc.parkinglot.GetAllRatingsOfParkingLotRequest;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotInformation;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc;
import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.emulator.configuration.SpringApplicationContext;

import io.grpc.StatusRuntimeException;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author bht
 */
@Log4j2
@EnableScheduling
@SpringBootApplication
@SuppressWarnings("all")
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
        AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub = SpringApplicationContext
                .getBean(AuthServiceGrpc.AuthServiceBlockingStub.class);

        ParkingLotInformation parkingLotInformation;
        Customer customer;
        ValidateResponse validateResponse;

        try {
            System.out.println(parkingLotServiceBlockingStub.countAllParkingLotHasRatings(CountAllParkingLotHasRatingsRequest.newBuilder()
                    .setLowerBoundRating(0)
                    .setUpperBoundRating(3)
                    .build()));

            System.out.println(parkingLotServiceBlockingStub.getAllParkingLotHasRatings(GetAllParkingLotHasRatingsRequest.newBuilder()
                    .setLowerBoundRating(0)
                    .setUpperBoundRating(3)
                    .setSortRatingAsc(false)
                    .setNRow(5)
                    .setPageNumber(1)
                    .build()));

            System.out.println(parkingLotServiceBlockingStub.countAllRatingsOfParkingLot(CountAllRatingsOfParkingLotRequest.newBuilder()
                    .setParkingLotId(109)
                    .setRating(5)
                    .build()));

            System.out.println(parkingLotServiceBlockingStub.getAllRatingsOfParkingLot(GetAllRatingsOfParkingLotRequest.newBuilder()
                    .setParkingLotId(109)
                    .setRating(5)
                    .setSortLastUpdatedAsc(false)
                    .setNRow(5)
                    .setPageNumber(1)
                    .build()));

//            System.out.println("\n\n" + userServiceBlockingStub.mapToUsernameList(MapToUsernameListRequest.newBuilder()
//                    .addAllUserId(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L)).build())
//                    .getUsernameMap() + "\n\n");
//
//            System.out.println("\n\n" + parkingLotServiceBlockingStub.countAllParkingLot(CountAllParkingLotRequest.newBuilder()
//                    .setParkingLotType(ParkingLotType.BUILDING)
//                    .setKeyword("Chung")
//                    .setAvailableOnly(true)
//                    .build()) + "\n\n");
//
//            System.out.println(parkingLotServiceBlockingStub.getAllParkingLot(GetAllParkingLotRequest.newBuilder()
//                    .setParkingLotType(ParkingLotType.BUILDING)
//                    .setKeyword("Chung")
//                    .setAvailableOnly(true)
//                    .setNRow(5)
//                    .setPageNumber(1)
//                    .build()));
//
//            System.out.println("\n\n" + userServiceBlockingStub.countAllUser(CountAllUserRequest.newBuilder()
//                    .setUserRole(UserRole.CUSTOMER)
//                    .setKeyword("tb")
//                    .setInactivatedOnly(true)
//                    .build()) + "\n\n");
//
//            System.out.println(userServiceBlockingStub.getAllUser(GetAllUserRequest.newBuilder()
//                    .setUserRole(UserRole.CUSTOMER)
//                    .setKeyword("tb")
//                    .setInactivatedOnly(true)
//                    .setNRow(5)
//                    .setPageNumber(1)
//                    .build()));

//            for (int i = 0; i < 30; i++) {
//                log.info("Start calling API");
//                validateResponse = authServiceBlockingStub.validateUser(ValidateRequest.newBuilder()
//                        .setUsername("htbinh")
//                        .setPassword("htbinh789")
//                        .setRole(UserRole.CUSTOMER)
//                        .build());
//                log.info("End calling API");
//                log.info("\n" + validateResponse);
//            }

//            log.info("Start calling API");
//            parkingLotInformation = parkingLotServiceBlockingStub.getParkingLotById(Int64Value.of(1)).getInformation();
//            log.info("End calling API");
//            log.info("\n" + parkingLotInformation);

//            log.info("Start calling API");
//            customer = userServiceBlockingStub.getCustomerById(Int64Value.of(4));
//            log.info("End calling API");
//            log.info("\n" + customer);

//            log.info("Start calling API");
//            parkingLotInformation = parkingLotServiceBlockingStub.getParkingLotById(Int64Value.of(3)).getInformation();
//            log.info("End calling API");
//            log.info("\n" + parkingLotInformation);

//            log.info("Start calling API");
//            customer = userServiceBlockingStub.getCustomerByUsername(StringValue.of("htbinh"));
//            log.info("End calling API");
//            log.info("\n" + customer);

        } catch (StatusRuntimeException exception) {

            switch (exception.getStatus().getCode()) {
                case UNAUTHENTICATED:
                    log.error("Unauthenticated user ! Please login !");
                    break;
                case PERMISSION_DENIED:
                    log.error("Permission denied ! Please login as another user !");
                    break;
                case UNIMPLEMENTED:
                    log.error("Service is unimplemented ! Please try another service !");
                    break;
                default:
                    log.error(exception.getStatus());
                    break;
            }
        }
    }
}