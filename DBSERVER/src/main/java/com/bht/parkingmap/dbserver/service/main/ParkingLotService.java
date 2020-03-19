package com.bht.parkingmap.dbserver.service.main;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotLimitEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    ParkingLotEntity getParkingLotById(@NotNull Long id);

    ParkingLotLimitEntity getParkingLotLimitById(@NotNull Long id);

    Boolean checkAvailability(@NotNull Long parkingLotId);

    List<Long> checkUnavailability(@NotEmpty List<Long> parkingLotIdList);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithoutName(@NotNull Double lat,
                                                                   @NotNull Double lng,
                                                                   @NotNull Integer radius,
                                                                   @NotNull Integer nResult);

    List<Tuple> getTopParkingLotInRegionOrderByDistanceWithName(@NotNull Double lat,
                                                                @NotNull Double lng,
                                                                @NotNull Integer radius,
                                                                @NotNull Integer nResult);
}