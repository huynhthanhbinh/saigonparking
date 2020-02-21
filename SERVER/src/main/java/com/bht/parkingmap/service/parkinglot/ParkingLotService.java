package com.bht.parkingmap.service.parkinglot;

import java.util.List;

import com.bht.parkingmap.api.parkinglot.ParkingLot;
import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    ParkingLotEntity getParkingLotById(long id);

    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(double northEastLat, double northEastLng, double southWestLat, double southWestLng);

    List<ParkingLot> getAllParkingLotCurrentlyWorkingInRegionOfRadius(double latitude, double longitude, int radiusInKilometre);
}