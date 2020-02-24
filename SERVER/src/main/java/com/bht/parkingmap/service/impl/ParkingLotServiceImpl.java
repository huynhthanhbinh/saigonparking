package com.bht.parkingmap.service.impl;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.entity.parkinglot.ParkingLotInformationEntity;
import com.bht.parkingmap.repository.parkinglot.ParkingLotInformationRepository;
import com.bht.parkingmap.repository.parkinglot.ParkingLotRepository;
import com.bht.parkingmap.service.ParkingLotService;

/**
 *
 * @author bht
 */
@Service
@Transactional
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotInformationRepository parkingLotInformationRepository;

    @Autowired
    public ParkingLotServiceImpl(ParkingLotRepository parkingLotRepository,
                                 ParkingLotInformationRepository parkingLotInformationRepository) {

        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotInformationRepository = parkingLotInformationRepository;
    }

    @Override
    public ParkingLotEntity getParkingLotById(long id) {
        return parkingLotRepository.getOne(id);
    }

    @Override
    public ParkingLotInformationEntity getParkingLotInformationByParkingLotId(long id) {
        return parkingLotInformationRepository.getOne(id);
    }

    @Override
    public List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(double northEastLat, double northEastLng, double southWestLat, double southWestLng) {
        return parkingLotRepository.getAllParkingLotCurrentlyWorkingInRegion(northEastLat, northEastLng, southWestLat, southWestLng);
    }

    @Override
    public List<Tuple> getAllParkingLotCurrentlyWorkingByRadius(double latitude, double longitude, int radiusInKilometre) {
        return parkingLotRepository.getAllParkingLotCurrentlyWorkingInRegionOfRadius(latitude, longitude, radiusInKilometre);
    }
}