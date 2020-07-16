package com.bht.saigonparking.service.booking.service.main.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.common.exception.BookingAlreadyFinishedException;
import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;
import com.bht.saigonparking.service.booking.repository.core.BookingHistoryRepository;
import com.bht.saigonparking.service.booking.repository.core.BookingRepository;
import com.bht.saigonparking.service.booking.service.main.BookingService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository bookingHistoryRepository;

    @Override
    public BookingEntity getBookingById(@NotNull Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BookingEntity getBookingDetailByBookingId(@NotNull Long bookingId) {
        return bookingRepository.getBookingDetailByBookingId(bookingId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Long saveNewBooking(@NotNull BookingEntity bookingEntity) {
        return bookingRepository.saveAndFlush(bookingEntity).getId();
    }

    @Override
    public void saveNewBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity, @NotNull Long bookingId) {
        BookingEntity bookingEntity = getBookingById(bookingId);
        if (bookingEntity.getIsFinished().equals(Boolean.FALSE)) {
            bookingHistoryEntity.setBookingEntity(bookingEntity);
            bookingHistoryRepository.saveAndFlush(bookingHistoryEntity);
            return;
        }
        throw new BookingAlreadyFinishedException();
    }

    @Override
    public void deleteBookingById(@NotNull Long bookingId) {
        bookingRepository.delete(getBookingById(bookingId));
    }

    @Override
    public Long countAllBooking() {
        return bookingRepository.countAllBooking();
    }

    @Override
    public Long countAllBooking(@NotNull BookingStatusEntity bookingStatusEntity) {
        return bookingRepository.countAllBooking(bookingStatusEntity);
    }

    @Override
    public Long countAllBookingOfCustomer(@NotNull Long customerId) {
        return bookingRepository.countAllBookingOfCustomer(customerId);
    }

    @Override
    public Long countAllBookingOfParkingLot(@NotNull Long parkingLotId) {
        return bookingRepository.countAllBookingOfParkingLot(parkingLotId);
    }

    @Override
    public Long countAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                            @NotNull BookingStatusEntity bookingStatusEntity) {
        return bookingRepository.countAllBookingOfParkingLot(parkingLotId, bookingStatusEntity);
    }

    @Override
    public Long countAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId) {
        return bookingRepository.countAllOnGoingBookingOfParkingLot(parkingLotId);
    }

    @Override
    public List<BookingEntity> getAllBooking(@NotNull Integer nRow,
                                             @NotNull Integer pageNumber) {

        return bookingRepository.getAllBooking(nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBooking(@NotNull BookingStatusEntity bookingStatusEntity,
                                             @NotNull Integer nRow,
                                             @NotNull Integer pageNumber) {

        return bookingRepository.getAllBooking(bookingStatusEntity, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBookingOfCustomer(@NotNull Long customerId,
                                                       @NotNull Integer nRow,
                                                       @NotNull Integer pageNumber) {

        return bookingRepository.getAllBookingOfCustomer(customerId, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                         @NotNull Integer nRow,
                                                         @NotNull Integer pageNumber) {

        return bookingRepository.getAllBookingOfParkingLot(parkingLotId, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                         @NotNull BookingStatusEntity bookingStatusEntity,
                                                         @NotNull Integer nRow,
                                                         @NotNull Integer pageNumber) {

        return bookingRepository.getAllBookingOfParkingLot(parkingLotId, bookingStatusEntity, nRow, pageNumber);
    }

    @Override
    public List<BookingEntity> getAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId) {
        return bookingRepository.getAllOnGoingBookingOfParkingLot(parkingLotId);
    }
}