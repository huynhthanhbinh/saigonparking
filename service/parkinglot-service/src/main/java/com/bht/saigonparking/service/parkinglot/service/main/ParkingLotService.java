package com.bht.saigonparking.service.parkinglot.service.main;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotLimitEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    Long countAll();

    ParkingLotEntity getParkingLotById(@NotNull Long id);

    ParkingLotLimitEntity getParkingLotLimitById(@NotNull Long id);

    List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber);

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