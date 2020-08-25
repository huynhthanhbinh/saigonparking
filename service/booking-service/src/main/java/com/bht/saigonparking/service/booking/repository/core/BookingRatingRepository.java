package com.bht.saigonparking.service.booking.repository.core;

import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bht.saigonparking.service.booking.entity.BookingRatingEntity;
import com.bht.saigonparking.service.booking.repository.custom.BookingRatingRepositoryCustom;

/**
 *
 * @author bht
 */
public interface BookingRatingRepository extends JpaRepository<BookingRatingEntity, Long>, BookingRatingRepositoryCustom {

    @Query("SELECT R " +
            "FROM BookingRatingEntity R " +
            "JOIN FETCH R.bookingEntity B " +
            "JOIN FETCH B.bookingStatusEntity " +
            "WHERE B.uuid = ?1")
    Optional<BookingRatingEntity> getByBookingUuid(@NotNull UUID uuid);
}