package com.bht.saigonparking.service.booking.repository.custom.impl;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;
import com.bht.saigonparking.service.booking.repository.custom.BookingRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
@Transactional
public class BookingRepositoryCustomImpl extends BaseRepositoryCustom implements BookingRepositoryCustom {

    @Override
    public Set<BookingEntity> getAllBooking(@NotNull Integer nRow,
                                            @NotNull Integer pageNumber) {

        return null;
    }

    @Override
    public Set<BookingEntity> getAllBooking(@NotNull BookingStatusEntity bookingStatusEntity,
                                            @NotNull Integer nRow,
                                            @NotNull Integer pageNumber) {

        return null;
    }

    @Override
    public Set<BookingEntity> getAllBookingOfCustomer(@NotNull Long customerId,
                                                      @NotNull Integer nRow,
                                                      @NotNull Integer pageNumber) {

        return null;
    }

    @Override
    public Set<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                        @NotNull Integer nRow,
                                                        @NotNull Integer pageNumber) {

        return null;
    }

    @Override
    public Set<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                        @NotNull BookingStatusEntity bookingStatusEntity,
                                                        @NotNull Integer nRow,
                                                        @NotNull Integer pageNumber) {

        return null;
    }

    @Override
    public Set<BookingEntity> getAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId) {
        
        return null;
    }
}