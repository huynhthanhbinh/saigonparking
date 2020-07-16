package com.bht.saigonparking.service.booking.repository.core;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.booking.entity.BookingEntity;

/**
 *
 * @author bht
 */
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("FROM BookingEntity B " +
            "JOIN FETCH B.bookingHistoryEntitySet H " +
            "JOIN FETCH H.bookingStatusEntity " +
            "WHERE B.id = ?1")
    Optional<BookingEntity> getBookingDetailByBookingId(@NotNull Long bookingId);
}