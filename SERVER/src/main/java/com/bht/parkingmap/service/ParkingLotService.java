package com.bht.parkingmap.service;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.entity.parkinglot.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    ParkingLotEntity getParkingLotById(@NotNull Long id);

    ParkingLotInformationEntity getParkingLotInformationByParkingLotId(@NotNull Long id);

    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(@NotNull Double northEastLat,
                                                                    @NotNull Double northEastLng,
                                                                    @NotNull Double southWestLat,
                                                                    @NotNull Double southWestLng);

    List<Tuple> getAllParkingLotCurrentlyWorkingByRadius(@NotNull Double latitude,
                                                         @NotNull Double longitude,
                                                         @NotNull Integer radiusInKilometre);
}