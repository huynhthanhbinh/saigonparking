package com.bht.parkingmap.appserver.mapper;

import javax.validation.constraints.NotNull;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLot;
import com.bht.parkingmap.appserver.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotMapperExt {

    ParkingLotEntity toParkingLotEntity(@NotNull ParkingLot parkingLot);
}