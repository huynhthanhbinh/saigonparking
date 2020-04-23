package com.bht.saigonparking.service.user.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.entity.parkinglot.ParkingLotUnitEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotUnitRepository extends JpaRepository<ParkingLotUnitEntity, Long> {
}