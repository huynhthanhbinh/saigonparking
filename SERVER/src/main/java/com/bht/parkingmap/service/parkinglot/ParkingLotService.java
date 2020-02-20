package com.bht.parkingmap.service.parkinglot;

import java.util.List;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;
import com.google.type.LatLng;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    ParkingLotEntity getParkingLotById(Long id);

    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(LatLng northWest, LatLng southEast);

    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegionOfRadius(LatLng coordinate, short radiusInKilometre);
}