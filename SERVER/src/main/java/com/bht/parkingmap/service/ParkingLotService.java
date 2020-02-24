package com.bht.parkingmap.service;

import java.util.List;

import javax.persistence.Tuple;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.entity.parkinglot.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
public interface ParkingLotService {

    ParkingLotEntity getParkingLotById(long id);

    ParkingLotInformationEntity getParkingLotInformationByParkingLotId(long id);

    List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(double northEastLat, double northEastLng, double southWestLat, double southWestLng);

    List<Tuple> getAllParkingLotCurrentlyWorkingByRadius(double latitude, double longitude, int radiusInKilometre);
}