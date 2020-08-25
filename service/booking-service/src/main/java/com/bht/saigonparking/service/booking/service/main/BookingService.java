package com.bht.saigonparking.service.booking.service.main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.util.Pair;

import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;

/**
 *
 * @author bht
 */
public interface BookingService {

    /* getOnGoingBookingOfCustomer not JOIN bookingHistorySet */
    BookingEntity getOnGoingBookingOfCustomer(@NotNull Long customerId);

    /* getBookingById not JOIN bookingHistorySet */
    BookingEntity getBookingByUuid(@NotEmpty String uuidString);

    /* getBookingById JOIN FETCH bookingHistorySet */
    BookingEntity getBookingDetailByUuid(@NotEmpty String uuidString);

    Pair<String, String> saveNewBooking(@NotNull BookingEntity bookingEntity);

    /* update status of one booking */
    void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity,
                               @NotEmpty String uuidString);

    void deleteBookingByUuid(@NotEmpty String uuidString);

    Pair<Long, Long> finishBooking(@NotEmpty String uuidString);

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

    boolean checkCustomerHasOnGoingBooking(@NotNull Long customerId);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId);

    Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId, @NotNull @Range(max = 5L) Integer rating);

    Map<Tuple, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                 boolean sortLastUpdatedAsc,
                                                 @NotNull @Max(20L) Integer nRow,
                                                 @NotNull Integer pageNumber);

    Map<Tuple, String> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                 @NotNull @Range(max = 5L) Integer rating,
                                                 boolean sortLastUpdatedAsc,
                                                 @NotNull @Max(20L) Integer nRow,
                                                 @NotNull Integer pageNumber);

    Map<Integer, Long> getParkingLotRatingCountGroupByRating(@NotNull Long parkingLotId);

    void createBookingRating(@NotNull Long customerId, @NotEmpty String bookingUuidString, @NotNull Integer rating, @NotEmpty String comment);

    void updateBookingRating(@NotNull Long customerId, @NotEmpty String bookingUuidString, @NotNull Integer rating, @NotEmpty String comment);

    void deleteBookingRating(@NotNull Long customerId, @NotEmpty String bookingUuidString);

    void createOneOrManyParkingLotStatistic(@NotNull Set<Long> parkingLotIdSet);

    void deleteOneOrManyParkingLotStatistic(@NotNull Set<Long> parkingLotIdSet);
}