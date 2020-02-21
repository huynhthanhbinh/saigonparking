package com.bht.parkingmap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.api.parkinglot.ParkingLot;
import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.mapper.parkinglot.ParkingLotMapper;
import com.bht.parkingmap.repository.parkinglot.ParkingLotRepository;
import com.bht.parkingmap.service.parkinglot.ParkingLotService;
import com.google.type.LatLng;

/**
 *
 * @author bht
 */
@Service
@Transactional
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapper parkingLotMapper;

    @Autowired
    public ParkingLotServiceImpl(ParkingLotRepository parkingLotRepository, ParkingLotMapper parkingLotMapper) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
    }

    @Override
    public ParkingLotEntity getParkingLotById(Long id) {
        return parkingLotRepository.getOne(id);
    }

    @Override
    public List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(LatLng northEast, LatLng southWest) {
        return parkingLotRepository.getAllParkingLotCurrentlyWorkingInRegion(
                northEast.getLatitude(),
                northEast.getLongitude(),
                southWest.getLatitude(),
                southWest.getLongitude());
    }

    @Override
    public List<ParkingLot> getAllParkingLotCurrentlyWorkingInRegionOfRadius(double latitude, double longitude, int radiusInKilometre) {
        return parkingLotMapper.toParkingLotList(parkingLotRepository
                .getAllParkingLotCurrentlyWorkingInRegionOfRadius(latitude, longitude, radiusInKilometre));
    }
}