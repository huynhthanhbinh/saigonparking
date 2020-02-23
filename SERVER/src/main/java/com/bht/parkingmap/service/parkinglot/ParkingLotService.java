package com.bht.parkingmap.service.parkinglot;

import java.util.List;

import javax.persistence.Tuple;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    ParkingLotEntity getParkingLotById(long id);

    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(double northEastLat, double northEastLng, double southWestLat, double southWestLng);

    List<Tuple> getAllParkingLotCurrentlyWorkingInRegionOfRadius(double latitude, double longitude, int radiusInKilometre);
}