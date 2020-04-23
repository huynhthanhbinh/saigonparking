package com.bht.saigonparking.appserver.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.appserver.entity.parkinglot.ParkingLotTypeEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotTypeRepository extends JpaRepository<ParkingLotTypeEntity, Long> {
}