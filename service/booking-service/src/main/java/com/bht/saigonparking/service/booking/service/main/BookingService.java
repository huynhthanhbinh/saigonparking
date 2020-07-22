package com.bht.saigonparking.service.booking.service.main;

import java.util.List;

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
    BookingEntity getBookingByUuid(@NotNull String uuidString);

    /* getBookingById JOIN FETCH bookingHistorySet */
    BookingEntity getBookingDetailByUuid(@NotNull String uuidString);

    String saveNewBooking(@NotNull BookingEntity bookingEntity);

    void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity, @NotNull String uuidString);

    void deleteBookingByUuid(@NotNull String uuidString);

    Long countAllBooking();

    Long countAllBooking(@NotNull BookingStatusEntity bookingStatusEntity);

    Long countAllBookingOfCustomer(@NotNull Long customerId);

    Long countAllBookingOfParkingLot(@NotNull Long parkingLotId);

    Long countAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                     @NotNull BookingStatusEntity bookingStatusEntity);

    Long countAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId);

    List<BookingEntity> getAllBooking(@NotNull Integer nRow,
                                      @NotNull Integer pageNumber);

    List<BookingEntity> getAllBooking(@NotNull BookingStatusEntity bookingStatusEntity,
                                      @NotNull Integer nRow,
                                      @NotNull Integer pageNumber);

    List<BookingEntity> getAllBookingOfCustomer(@NotNull Long customerId,
                                                @NotNull Integer nRow,
                                                @NotNull Integer pageNumber);

    List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                  @NotNull Integer nRow,
                                                  @NotNull Integer pageNumber);

    List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                  @NotNull BookingStatusEntity bookingStatusEntity,
                                                  @NotNull Integer nRow,
                                                  @NotNull Integer pageNumber);

    List<BookingEntity> getAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId);
}