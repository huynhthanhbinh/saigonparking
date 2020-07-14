package com.bht.saigonparking.service.booking.service.main;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.booking.entity.BookingEntity;

/**
 *
 * @author bht
 */
public interface BookingService {

    Long saveNewBooking(@NotNull BookingEntity bookingEntity);
}