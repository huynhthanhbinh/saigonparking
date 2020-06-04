package com.bht.saigonparking.service.parkinglot.mapper;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLot;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotMapperExt {

    ParkingLotEntity toParkingLotEntity(@NotNull ParkingLot parkingLot, boolean isAboutToCreate);
}