package com.bht.parkingmap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.entity.parkinglot.ParkingLotInformationEntity;
import com.bht.parkingmap.repository.parkinglot.ParkingLotInformationRepository;
import com.bht.parkingmap.service.parkinglot.ParkingLotInformationService;

/**
 *
 * @author bht
 */
@Service
@Transactional
public class ParkingLotInformationServiceImpl implements ParkingLotInformationService {

    private final ParkingLotInformationRepository parkingLotInformationRepository;

    @Autowired
    public ParkingLotInformationServiceImpl(ParkingLotInformationRepository parkingLotInformationRepository) {
        this.parkingLotInformationRepository = parkingLotInformationRepository;
    }

    @Override
    public ParkingLotInformationEntity getParkingLotInformationById(Long id) {
        return parkingLotInformationRepository.getOne(id);
    }
}