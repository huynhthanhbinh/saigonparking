package com.bht.saigonparking.appserver.mapper;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.proto.parkinglot.ParkingLot;
import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotMapperExt {

    ParkingLotEntity toParkingLotEntity(@NotNull ParkingLot parkingLot);
}