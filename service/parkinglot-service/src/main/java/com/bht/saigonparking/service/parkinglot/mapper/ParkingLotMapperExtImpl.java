package com.bht.saigonparking.service.parkinglot.mapper;

import java.util.Collections;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLot;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotInformation;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotLimitEntity;
import com.bht.saigonparking.service.parkinglot.repository.core.ParkingLotRepository;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ParkingLotMapperExtImpl implements ParkingLotMapperExt {

    private final EnumMapper enumMapper;
    private final CustomizedMapper customizedMapper;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public final ParkingLotEntity toParkingLotEntity(@NotNull ParkingLot parkingLot, boolean isAboutToCreate) {

        ParkingLotEntity parkingLotEntity;
        ParkingLotLimitEntity parkingLotLimitEntity;
        ParkingLotInformationEntity parkingLotInformationEntity;

        if (!isAboutToCreate) {

            /* case: update parkingLotEntity */
            parkingLotEntity = parkingLotRepository.getById(parkingLot.getId()).orElseThrow(EntityNotFoundException::new);
            parkingLotLimitEntity = parkingLotEntity.getParkingLotLimitEntity();
            parkingLotInformationEntity = parkingLotEntity.getParkingLotInformationEntity();
            parkingLotEntity.setVersion(parkingLot.getVersion());

            parkingLotLimitEntity.setTotalSlot((short) parkingLot.getTotalSlot());
            parkingLotLimitEntity.setAvailableSlot((short) parkingLot.getAvailableSlot());

            ParkingLotInformation parkingLotInformation = parkingLot.getInformation();
            parkingLotInformationEntity.setName(parkingLotInformation.getName());
            parkingLotInformationEntity.setAddress(parkingLotInformation.getAddress());
            parkingLotInformationEntity.setPhone(parkingLotInformation.getPhone().isEmpty() ? "" : parkingLotInformation.getPhone());

        } else {

            /* case: create parkingLotEntity */
            parkingLotEntity = new ParkingLotEntity();
            parkingLotEntity.setVersion(1L);
            parkingLotEntity.setParkingLotUnitEntitySet(Collections.emptySet());
            parkingLotEntity.setParkingLotEmployeeEntitySet(Collections.emptySet());
            parkingLotEntity.setParkingLotTypeEntity(enumMapper.toParkingLotTypeEntity(parkingLot.getType()));
        }

        parkingLotEntity.setLatitude(parkingLot.getLatitude());
        parkingLotEntity.setLongitude(parkingLot.getLongitude());
        parkingLotEntity.setOpeningHour(customizedMapper.toTime(parkingLot.getOpeningHour()));
        parkingLotEntity.setClosingHour(customizedMapper.toTime(parkingLot.getClosingHour()));

        return parkingLotEntity;
    }
}