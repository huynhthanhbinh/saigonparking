package com.bht.parkingmap.dbserver.service.impl;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotInformationEntity;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotInformationRepository;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotRepository;
import com.bht.parkingmap.dbserver.service.ParkingLotService;

import lombok.AllArgsConstructor;

/**
 *
 * this class implements all services relevant to ParkingLot
 *
 * for clean code purpose,
 * using {@code @AllArgsConstructor} for Service class
 * it will {@code @Autowired} all attributes declared inside
 * hide {@code @Autowired} as much as possible in code
 * remember to mark all attributes as {@code private final}
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotInformationRepository parkingLotInformationRepository;

    @Override
    public ParkingLotEntity getParkingLotById(@NotNull Long id) {
        return parkingLotRepository.getOne(id);
    }

    @Override
    public ParkingLotInformationEntity getParkingLotInformationByParkingLotId(@NotNull Long id) {
        return parkingLotInformationRepository.getOne(id);
    }

    @Override
    public List<Tuple> getTopParkingLotInRegionOrderByDistanceWithoutName(@NotNull Double lat, @NotNull Double lng, @NotNull Integer radius, @NotNull Integer nResult) {
        return parkingLotRepository.getTopParkingLotInRegionOrderByDistanceWithoutName(lat, lng, radius, nResult);
    }

    @Override
    public List<Tuple> getTopParkingLotInRegionOrderByDistanceWithName(@NotNull Double lat, @NotNull Double lng, @NotNull Integer radius, @NotNull Integer nResult) {
        return parkingLotRepository.getTopParkingLotInRegionOrderByDistanceWithName(lat, lng, radius, nResult);
    }
}