package com.bht.saigonparking.service.parkinglot.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotEmployeeRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotEmployeeRepository extends JpaRepository<ParkingLotEmployeeEntity, Long>, ParkingLotEmployeeRepositoryCustom {
}