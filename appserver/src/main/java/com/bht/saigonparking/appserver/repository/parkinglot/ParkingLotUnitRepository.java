package com.bht.saigonparking.appserver.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotUnitEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotUnitRepository extends JpaRepository<ParkingLotUnitEntity, Long> {
}