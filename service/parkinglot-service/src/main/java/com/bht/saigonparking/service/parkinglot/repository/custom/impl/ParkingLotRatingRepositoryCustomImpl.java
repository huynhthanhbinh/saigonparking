package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseEntity_;
import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity_;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotRatingRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public class ParkingLotRatingRepositoryCustomImpl extends BaseRepositoryCustom implements ParkingLotRatingRepositoryCustom {

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotRatingEntity> root = query.from(ParkingLotRatingEntity.class);

        Join<ParkingLotRatingEntity, ParkingLotEntity> parkingLotEntityJoin = root
                .join(ParkingLotRatingEntity_.parkingLotEntity);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.equal(parkingLotEntityJoin.get(BaseEntity_.id), parkingLotId)))
                .getSingleResult();
    }

    @Override
    public Long countAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                            @NotNull @Range(min = 1L, max = 5L) Integer rating) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotRatingEntity> root = query.from(ParkingLotRatingEntity.class);

        Join<ParkingLotRatingEntity, ParkingLotEntity> parkingLotEntityJoin = root
                .join(ParkingLotRatingEntity_.parkingLotEntity);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(parkingLotEntityJoin.get(BaseEntity_.id), parkingLotId),
                        criteriaBuilder.equal(root.get(ParkingLotRatingEntity_.rating), rating))))
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

        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        Root<ParkingLotRatingEntity> root = query.from(ParkingLotRatingEntity.class);

        Join<ParkingLotRatingEntity, ParkingLotEntity> parkingLotEntityJoin = root
                .join(ParkingLotRatingEntity_.parkingLotEntity);

        TypedQuery<Tuple> getCountGroupByQuery = entityManager
                .createQuery(query
                        .multiselect(root.get(ParkingLotRatingEntity_.rating), criteriaBuilder.count(root))
                        .where(criteriaBuilder.equal(parkingLotEntityJoin.get(BaseEntity_.id), parkingLotId))
                        .groupBy(root.get(ParkingLotRatingEntity_.rating)));

        return getCountGroupByQuery.getResultList().stream().collect(Collectors
                .toMap(record -> record.get(0, Short.class).intValue(), record -> record.get(1, Long.class)));
    }
}