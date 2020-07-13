package com.bht.saigonparking.service.booking.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.booking.entity.BookingEntity;

/**
 *
 * @author bht
 */
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}