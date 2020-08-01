package com.bht.saigonparking.service.booking.repository.core;

import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.repository.custom.BookingRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long>, BookingRepositoryCustom {

    Optional<BookingEntity> getBookingByUuid(@NotNull UUID uuid);

    @Query("SELECT B " +
            "FROM BookingEntity B " +
            "JOIN FETCH B.bookingHistoryEntitySet H " +
            "JOIN FETCH H.bookingStatusEntity " +
            "WHERE B.uuid = ?1")
    Optional<BookingEntity> getBookingDetailByUuid(@NotNull UUID uuid);

    /**
     *
     * self-implement countUnfinishedBookingByUserId method
     * using to check if username already exist
     */
    @Query("SELECT COUNT (B.id) " +
            "FROM BookingEntity B " +
            "WHERE B.customerId = ?1 AND B.isFinished = FALSE")
    Long countAllUnfinishedBookingByCustomerId(@NotNull Long userId);
}