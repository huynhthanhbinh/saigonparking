package com.bht.saigonparking.service.parkinglot.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotUnitEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotUnitRepository extends JpaRepository<ParkingLotUnitEntity, Long> {
}