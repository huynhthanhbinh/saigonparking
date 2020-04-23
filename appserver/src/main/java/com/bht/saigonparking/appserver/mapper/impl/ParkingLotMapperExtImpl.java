package com.bht.saigonparking.appserver.mapper.impl;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLot;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotInformation;
import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotEntity;
import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotInformationEntity;
import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotLimitEntity;
import com.bht.saigonparking.appserver.exception.ConcurrentUpdateException;
import com.bht.saigonparking.appserver.mapper.CustomizedMapper;
import com.bht.saigonparking.appserver.mapper.ParkingLotMapperExt;
import com.bht.saigonparking.appserver.repository.parkinglot.ParkingLotRepository;

import lombok.AllArgsConstructor;

/**
 *
 * Parking Lot Mapper Extension
 * this class implement override methods of ParkingLotMapper
 *
 * Note that Ext class in declared as final non-public
 * in order to hide this class from outside of mapper package
 * It is expected to be seen only by mapper class, here is ParkingLotMapper
 *
 * for using repository inside Component class,
 * we need to {@code @Autowired} it by Spring Dependency Injection
 * we can achieve that easily
 * by using {@code @Setter(onMethod = @__(@Autowired)} for class level like below
 *
 * we cannot use {@code @AllArgsConstructor} for class level,
 * because these repository/injected fields are optional,
 * and it will conflict with {@code @Mapper @Component} bean
 * which will be initialized by NonArgsConstructor !!!!!!!!!
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ParkingLotMapperExtImpl implements ParkingLotMapperExt {

    private final CustomizedMapper customizedMapper;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public final ParkingLotEntity toParkingLotEntity(@NotNull ParkingLot parkingLot) {

        long parkingLotId = parkingLot.getId();
        ParkingLotEntity parkingLotEntity;
        ParkingLotInformationEntity parkingLotInformationEntity;
        ParkingLotInformation parkingLotInformation = parkingLot.getInformation();

        if (parkingLotId != 0) {    // update parkingLotEntity
            parkingLotEntity = parkingLotRepository.getById(parkingLotId);
            parkingLotInformationEntity = parkingLotEntity.getParkingLotInformationEntity();

            if (parkingLotEntity.getVersion() != parkingLot.getVersion()
                    || parkingLotInformationEntity.getVersion() != parkingLotInformation.getVersion()) {
                String message = "Parking-lot has just been updated by another tx, please refresh and try again to update";
                throw new ConcurrentUpdateException(message);
            }

        } else {                    // create parkingLotEntity
            parkingLotEntity = new ParkingLotEntity();
            parkingLotInformationEntity = new ParkingLotInformationEntity();

            parkingLotEntity.setParkingLotLimitEntity(ParkingLotLimitEntity.builder()
                    .availableSlot((short) parkingLot.getAvailableSlot())
                    .totalSlot((short) parkingLot.getTotalSlot())
                    .build());
        }

        parkingLotInformationEntity.setName(parkingLotInformation.getName());
        parkingLotInformationEntity.setAddress(parkingLotInformation.getAddress());
        parkingLotInformationEntity.setPhone(parkingLotInformation.getPhone().isEmpty() ? null : parkingLotInformation.getPhone());

        parkingLotEntity.setLatitude(parkingLot.getLatitude());
        parkingLotEntity.setLongitude(parkingLot.getLongitude());
        parkingLotEntity.setOpeningHour(customizedMapper.toTime(parkingLot.getOpeningHour()));
        parkingLotEntity.setClosingHour(customizedMapper.toTime(parkingLot.getClosingHour()));
        parkingLotEntity.setParkingLotInformationEntity(parkingLotInformationEntity);

        return parkingLotEntity;
    }
}