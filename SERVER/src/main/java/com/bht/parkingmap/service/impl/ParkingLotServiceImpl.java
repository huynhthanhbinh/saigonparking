package com.bht.parkingmap.service.impl;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;

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
    public ParkingLotEntity getParkingLotById(@NotNull Long id) {
        return parkingLotRepository.getOne(id);
    }

    @Override
    public ParkingLotInformationEntity getParkingLotInformationByParkingLotId(@NotNull Long id) {
        return parkingLotInformationRepository.getOne(id);
    }

    @Override
    public List<ParkingLotEntity> getAllParkingLotCurrentlyWorkingInRegion(@NotNull Double northEastLat, @NotNull Double northEastLng, @NotNull Double southWestLat, @NotNull Double southWestLng) {
        return parkingLotRepository.getAllParkingLotCurrentlyWorkingInRegion(northEastLat, northEastLng, southWestLat, southWestLng);
    }

    @Override
    public List<Tuple> getAllParkingLotCurrentlyWorkingByRadius(@NotNull Double latitude, @NotNull Double longitude, @NotNull Integer radiusInKilometre) {
        return parkingLotRepository.getAllParkingLotCurrentlyWorkingInRegionOfRadius(latitude, longitude, radiusInKilometre);
    }
}