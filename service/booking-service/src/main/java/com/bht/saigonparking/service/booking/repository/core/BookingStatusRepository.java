package com.bht.saigonparking.service.booking.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;

/**
 *
 * @author bht
 */
@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatusEntity, Long> {
}