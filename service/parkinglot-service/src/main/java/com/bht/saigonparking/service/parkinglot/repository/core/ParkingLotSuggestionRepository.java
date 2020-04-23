package com.bht.saigonparking.service.parkinglot.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.parkinglot.entity.ParkingLotSuggestionEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotSuggestionRepository extends JpaRepository<ParkingLotSuggestionEntity, Long> {
}