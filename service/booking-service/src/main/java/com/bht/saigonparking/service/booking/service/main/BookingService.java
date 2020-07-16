package com.bht.saigonparking.service.booking.service.main;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;

/**
 *
 * @author bht
 */
public interface BookingService {

    /* getBookingById not JOIN bookingHistorySet */
    BookingEntity getBookingById(@NotNull Long bookingId);

    /* getBookingById JOIN FETCH bookingHistorySet */
    BookingEntity getBookingDetailByBookingId(@NotNull Long bookingId);

    Long saveNewBooking(@NotNull BookingEntity bookingEntity);

    void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity, @NotNull Long bookingId);

    void deleteBookingById(@NotNull Long bookingId);

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