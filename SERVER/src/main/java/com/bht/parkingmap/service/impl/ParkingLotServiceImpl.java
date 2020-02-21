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
    public ParkingLotEntity getParkingLotById(long id) {
        return parkingLotRepository.getOne(id);
    }

    @Override
    public List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(double northEastLat, double northEastLng, double southWestLat, double southWestLng) {
        return parkingLotRepository.getAllParkingLotCurrentlyWorkingInRegion(northEastLat, northEastLng, southWestLat, southWestLng);
    }

    @Override
    public List<ParkingLot> getAllParkingLotCurrentlyWorkingInRegionOfRadius(double latitude, double longitude, int radiusInKilometre) {
        return parkingLotMapper.toParkingLotListWithDistance(parkingLotRepository
                .getAllParkingLotCurrentlyWorkingInRegionOfRadius(latitude, longitude, radiusInKilometre));
    }
}