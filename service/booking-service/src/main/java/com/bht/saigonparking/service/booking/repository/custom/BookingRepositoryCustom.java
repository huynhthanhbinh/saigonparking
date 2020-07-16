package com.bht.saigonparking.service.booking.repository.custom;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;

/**
 *
 * @author bht
 */
public interface BookingRepositoryCustom {

    Set<BookingEntity> getAllBooking(@NotNull Integer nRow,
                                     @NotNull Integer pageNumber);

    Set<BookingEntity> getAllBooking(@NotNull BookingStatusEntity bookingStatusEntity,
                                     @NotNull Integer nRow,
                                     @NotNull Integer pageNumber);

    Set<BookingEntity> getAllBookingOfCustomer(@NotNull Long customerId,
                                               @NotNull Integer nRow,
                                               @NotNull Integer pageNumber);

    Set<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                 @NotNull Integer nRow,
                                                 @NotNull Integer pageNumber);

    Set<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                 @NotNull BookingStatusEntity bookingStatusEntity,
                                                 @NotNull Integer nRow,
                                                 @NotNull Integer pageNumber);

    Set<BookingEntity> getAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId);
}