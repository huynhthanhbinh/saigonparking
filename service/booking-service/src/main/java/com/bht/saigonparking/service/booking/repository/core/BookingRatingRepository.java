package com.bht.saigonparking.service.booking.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bht.saigonparking.service.booking.entity.BookingRatingEntity;
import com.bht.saigonparking.service.booking.repository.custom.BookingRatingRepositoryCustom;

/**
 *
 * @author bht
 */
public interface BookingRatingRepository extends JpaRepository<BookingRatingEntity, Long>, BookingRatingRepositoryCustom {
}