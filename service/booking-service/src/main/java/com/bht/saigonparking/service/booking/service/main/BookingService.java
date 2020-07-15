package com.bht.saigonparking.service.booking.service.main;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;

/**
 *
 * @author bht
 */
public interface BookingService {

    BookingEntity getBookingById(@NotNull Long bookingId);

    Long saveNewBooking(@NotNull BookingEntity bookingEntity);

    void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity, @NotNull Long bookingId);
}