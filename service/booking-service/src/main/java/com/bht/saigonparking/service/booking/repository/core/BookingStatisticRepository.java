package com.bht.saigonparking.service.booking.repository.core;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bht.saigonparking.service.booking.entity.BookingStatisticEntity;

/**
 *
 * @author bht
 */
public interface BookingStatisticRepository extends JpaRepository<BookingStatisticEntity, Long> {

    Optional<BookingStatisticEntity> getByParkingLotId(@NotNull Long parkingLotId);
}