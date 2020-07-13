package com.bht.saigonparking.service.booking.repository.core;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;

/**
 *
 * @author bht
 */
@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatusEntity, Long> {

    Optional<BookingStatusEntity> findByStatus(@NotEmpty String status);
}