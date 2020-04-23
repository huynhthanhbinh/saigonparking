package com.bht.saigonparking.service.user.repository.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.entity.parkinglot.ParkingLotRatingEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotRatingRepository extends JpaRepository<ParkingLotRatingEntity, Long> {
}