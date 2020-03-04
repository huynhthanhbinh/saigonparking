package com.bht.parkingmap.dbserver.service;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;

import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    ParkingLotEntity getParkingLotById(@NotNull Long id);

    ParkingLotInformationEntity getParkingLotInformationByParkingLotId(@NotNull Long id);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithoutName(@NotNull Double lat,
                                                                   @NotNull Double lng,
                                                                   @NotNull Integer radius,
                                                                   @NotNull Integer nResult);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithName(@NotNull Double lat,
                                                                @NotNull Double lng,
                                                                @NotNull Integer radius,
                                                                @NotNull Integer nResult);
}