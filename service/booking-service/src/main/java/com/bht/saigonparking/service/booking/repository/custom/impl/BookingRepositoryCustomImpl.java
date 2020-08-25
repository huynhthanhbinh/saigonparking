package com.bht.saigonparking.service.booking.repository.custom.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;
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
    public List<Tuple> countAllBookingGroupByStatus() {
        String getCountGroupByQuery = "SELECT B.bookingStatusEntity.id, COUNT(B.id) " +
                "FROM BookingEntity B " +
                "GROUP BY B.bookingStatusEntity.id ";

        return entityManager.createQuery(getCountGroupByQuery, Tuple.class)
                .getResultList();
    }

    @Override
    public List<Tuple> countAllBookingOfParkingLotGroupByStatus(@NotNull Long parkingLotId) {
        String getCountGroupByQuery = "SELECT B.bookingStatusEntity.id, COUNT(B.id) " +
                "FROM BookingEntity B " +
                "WHERE B.parkingLotId = :parkingLotId " +
                "GROUP BY B.bookingStatusEntity.id ";

        return entityManager.createQuery(getCountGroupByQuery, Tuple.class)
                .setParameter("parkingLotId", parkingLotId)
                .getResultList();
    }

    @Override
    public Long countAllBooking() {

        String countAllQuery = "SELECT COUNT (B.id) " +
                "FROM BookingEntity B ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .getSingleResult();
    }

    @Override
    public Long countAllBooking(@NotNull BookingStatusEntity bookingStatusEntity) {

        String countAllQuery = "SELECT COUNT (B.id) " +
                "FROM BookingEntity B " +
                "WHERE B.bookingStatusEntity = :bookingStatusEntity ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .setParameter("bookingStatusEntity", bookingStatusEntity)
                .getSingleResult();
    }

    @Override
    public Long countAllBookingOfCustomer(@NotNull Long customerId) {

        String countAllQuery = "SELECT COUNT (B.id) " +
                "FROM BookingEntity B " +
                "WHERE B.customerId = :customerId ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .setParameter("customerId", customerId)
                .getSingleResult();
    }

    @Override
    public Long countAllBookingOfParkingLot(@NotNull Long parkingLotId) {

        String countAllQuery = "SELECT COUNT (B.id) " +
                "FROM BookingEntity B " +
                "WHERE B.parkingLotId = :parkingLotId ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .setParameter("parkingLotId", parkingLotId)
                .getSingleResult();
    }

    @Override
    public Long countAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                            @NotNull BookingStatusEntity bookingStatusEntity) {

        String countAllQuery = "SELECT COUNT (B.id) " +
                "FROM BookingEntity B " +
                "WHERE B.parkingLotId = :parkingLotId " +
                "AND B.bookingStatusEntity = :bookingStatusEntity ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .setParameter("parkingLotId", parkingLotId)
                .setParameter("bookingStatusEntity", bookingStatusEntity)
                .getSingleResult();
    }

    @Override
    public Long countAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId) {

        String countAllQuery = "SELECT COUNT (B.id) " +
                "FROM BookingEntity B " +
                "WHERE B.parkingLotId = :parkingLotId " +
                "AND B.isFinished = FALSE ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .setParameter("parkingLotId", parkingLotId)
                .getSingleResult();
    }

    @Override
    public List<BookingEntity> getAllBooking(@NotNull Integer nRow,
                                             @NotNull Integer pageNumber) {

        String getAllQuery = "SELECT B " +
                "FROM BookingEntity B " +
                "JOIN FETCH B.bookingStatusEntity " +
                "LEFT JOIN FETCH B.bookingRatingEntity " +
                "ORDER BY B.id DESC ";

        return entityManager.createQuery(getAllQuery, BookingEntity.class)
                .setFirstResult(nRow * (pageNumber - 1))
                .setMaxResults(nRow)
                .getResultList();
    }

    @Override
    public List<BookingEntity> getAllBooking(@NotNull BookingStatusEntity bookingStatusEntity,
                                             @NotNull Integer nRow,
                                             @NotNull Integer pageNumber) {

        String getAllQuery = "SELECT B " +
                "FROM BookingEntity B " +
                "JOIN FETCH B.bookingStatusEntity " +
                "LEFT JOIN FETCH B.bookingRatingEntity " +
                "WHERE B.bookingStatusEntity = :bookingStatusEntity " +
                "ORDER BY B.id DESC ";

        return entityManager.createQuery(getAllQuery, BookingEntity.class)
                .setParameter("bookingStatusEntity", bookingStatusEntity)
                .setFirstResult(nRow * (pageNumber - 1))
                .setMaxResults(nRow)
                .getResultList();
    }

    @Override
    public List<BookingEntity> getAllBookingOfCustomer(@NotNull Long customerId,
                                                       @NotNull Integer nRow,
                                                       @NotNull Integer pageNumber) {

        String getAllQuery = "SELECT B " +
                "FROM BookingEntity B " +
                "JOIN FETCH B.bookingStatusEntity " +
                "LEFT JOIN FETCH B.bookingRatingEntity " +
                "WHERE B.customerId = :customerId " +
                "ORDER BY B.id DESC ";

        return entityManager.createQuery(getAllQuery, BookingEntity.class)
                .setParameter("customerId", customerId)
                .setFirstResult(nRow * (pageNumber - 1))
                .setMaxResults(nRow)
                .getResultList();
    }

    @Override
    public List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                         @NotNull Integer nRow,
                                                         @NotNull Integer pageNumber) {

        String getAllQuery = "SELECT B " +
                "FROM BookingEntity B " +
                "JOIN FETCH B.bookingStatusEntity " +
                "LEFT JOIN FETCH B.bookingRatingEntity " +
                "WHERE B.parkingLotId = :parkingLotId " +
                "ORDER BY B.id DESC ";

        return entityManager.createQuery(getAllQuery, BookingEntity.class)
                .setParameter("parkingLotId", parkingLotId)
                .setFirstResult(nRow * (pageNumber - 1))
                .setMaxResults(nRow)
                .getResultList();
    }

    @Override
    public List<BookingEntity> getAllBookingOfParkingLot(@NotNull Long parkingLotId,
                                                         @NotNull BookingStatusEntity bookingStatusEntity,
                                                         @NotNull Integer nRow,
                                                         @NotNull Integer pageNumber) {

        String getAllQuery = "SELECT B " +
                "FROM BookingEntity B " +
                "JOIN FETCH B.bookingStatusEntity " +
                "LEFT JOIN FETCH B.bookingRatingEntity " +
                "WHERE B.parkingLotId = :parkingLotId " +
                "AND B.bookingStatusEntity = :bookingStatusEntity " +
                "ORDER BY B.id DESC ";

        return entityManager.createQuery(getAllQuery, BookingEntity.class)
                .setParameter("parkingLotId", parkingLotId)
                .setParameter("bookingStatusEntity", bookingStatusEntity)
                .setFirstResult(nRow * (pageNumber - 1))
                .setMaxResults(nRow)
                .getResultList();
    }

    @Override
    public List<BookingEntity> getAllOnGoingBookingOfParkingLot(@NotNull Long parkingLotId) {

        String getAllQuery = "SELECT B " +
                "FROM BookingEntity B " +
                "JOIN FETCH B.bookingStatusEntity " +
                "LEFT JOIN FETCH B.bookingRatingEntity " +
                "WHERE B.parkingLotId = :parkingLotId " +
                "AND B.isFinished = FALSE " +
                "ORDER BY B.id DESC ";

        return entityManager.createQuery(getAllQuery, BookingEntity.class)
                .setParameter("parkingLotId", parkingLotId)
                .getResultList();
    }

    @Override
    public Optional<BookingEntity> getFirstByCustomerIdAndIsFinished(@NotNull Long customerId,
                                                                     @NotNull Boolean isFinished) {

        String query = "SELECT B " +
                "FROM BookingEntity B " +
                "JOIN FETCH B.bookingStatusEntity " +
                "LEFT JOIN FETCH B.bookingRatingEntity " +
                "WHERE B.customerId = :customerId " +
                "AND B.isFinished = :isFinished ";

        return Optional.ofNullable(entityManager
                .createQuery(query, BookingEntity.class)
                .setParameter("customerId", customerId)
                .setParameter("isFinished", isFinished)
                .setMaxResults(1)
                .getSingleResult());
    }
}