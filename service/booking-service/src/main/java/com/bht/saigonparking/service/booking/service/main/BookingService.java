package com.bht.saigonparking.service.booking.service.main;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.util.Pair;

import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;

/**
 *
 * @author bht
 */
public interface BookingService {

    /* getBookingById not JOIN bookingHistorySet */
    BookingEntity getBookingByUuid(@NotEmpty String uuidString);

    /* getBookingById JOIN FETCH bookingHistorySet */
    BookingEntity getBookingDetailByUuid(@NotEmpty String uuidString);

    Pair<String, String> saveNewBooking(@NotNull BookingEntity bookingEntity);

    void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity, @NotEmpty String uuidString);

    void deleteBookingByUuid(@NotEmpty String uuidString);

    void finishBooking(@NotEmpty String uuidString);

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

    Map<Long, Long> countAllBookingGroupByStatus();

    Map<Long, Long> countAllBookingOfParkingLotGroupByStatus(@NotNull Long parkingLotId);
}