package com.bht.saigonparking.service.booking.repository.custom.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.booking.repository.custom.BookingRatingRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public class BookingRatingRepositoryCustomImpl extends BaseRepositoryCustom implements BookingRatingRepositoryCustom {

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId) {

        String countAllQuery = "SELECT COUNT(PR.id) " +
                "FROM ParkingLotRatingEntity PR " +
                "WHERE PR.parkingLotEntity.id = :parkingLotId ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .setParameter("parkingLotId", parkingLotId)
                .getSingleResult();
    }

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                            @NotNull @Range(min = 1L, max = 5L) Integer rating) {

        String countAllQuery = "SELECT COUNT(PR.id) " +
                "FROM ParkingLotRatingEntity PR " +
                "WHERE PR.parkingLotEntity.id = :parkingLotId " +
                "AND PR.rating = :rating ";

        return entityManager.createQuery(countAllQuery, Long.class)
                .setParameter("parkingLotId", parkingLotId)
                .setParameter("rating", rating.shortValue())
                .getSingleResult();
    }

    @Override
    public List<Tuple> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                 boolean sortLastUpdatedAsc,
                                                 @NotNull @Max(20L) Integer nRow,
                                                 @NotNull Integer pageNumber) {

        String getAllQuery = "SELECT PR.id, PR.parkingLotEntity.id, PR.customerId, PR.rating, PR.comment, PR.lastUpdated " +
                "FROM ParkingLotRatingEntity PR " +
                "WHERE PR.parkingLotEntity.id = :parkingLotId " +
                "ORDER BY PR.lastUpdated " + (sortLastUpdatedAsc ? " ASC " : " DESC ");

        return entityManager.createQuery(getAllQuery, Tuple.class)
                .setParameter("parkingLotId", parkingLotId)
                .setMaxResults(nRow)
                .setFirstResult(nRow * (pageNumber - 1))
                .getResultList();
    }

    @Override
    public List<Tuple> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                 @NotNull @Range(min = 1L, max = 5L) Integer rating,
                                                 boolean sortLastUpdatedAsc,
                                                 @NotNull @Max(20L) Integer nRow,
                                                 @NotNull Integer pageNumber) {

        String getAllQuery = "SELECT PR.id, PR.parkingLotEntity.id, PR.customerId, PR.rating, PR.comment, PR.lastUpdated " +
                "FROM ParkingLotRatingEntity PR " +
                "WHERE PR.parkingLotEntity.id = :parkingLotId " +
                "AND PR.rating = :rating " +
                "ORDER BY PR.lastUpdated " + (sortLastUpdatedAsc ? " ASC " : " DESC ");

        return entityManager.createQuery(getAllQuery, Tuple.class)
                .setParameter("parkingLotId", parkingLotId)
                .setParameter("rating", rating.shortValue())
                .setMaxResults(nRow)
                .setFirstResult(nRow * (pageNumber - 1))
                .getResultList();
    }

    @Override
    public Map<Integer, Long> getParkingLotRatingCountGroupByRating(@NotNull Long parkingLotId) {

        String getCountGroupByQuery = "SELECT PR.rating, COUNT(PR.id) " +
                "FROM ParkingLotRatingEntity PR " +
                "WHERE PR.parkingLotEntity.id = :parkingLotId " +
                "GROUP BY PR.rating ";

        return entityManager.createQuery(getCountGroupByQuery, Tuple.class)
                .setParameter("parkingLotId", parkingLotId)
                .getResultList().stream()
                .collect(Collectors
                        .toMap(record -> record.get(0, Short.class).intValue(), record -> record.get(1, Long.class)));
    }
}