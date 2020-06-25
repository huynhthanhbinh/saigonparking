package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseEntity_;
import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity_;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotRatingEntity_;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotRatingRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
@SuppressWarnings("unchecked")
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
    public List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                  boolean sortLastUpdatedAsc,
                                                                  @NotNull @Max(20L) Integer nRow,
                                                                  @NotNull Integer pageNumber) {

        CriteriaQuery<ParkingLotRatingEntity> query = criteriaBuilder.createQuery(ParkingLotRatingEntity.class);
        Root<ParkingLotRatingEntity> root = query.from(ParkingLotRatingEntity.class);

        Fetch<ParkingLotRatingEntity, ParkingLotEntity> parkingLotEntityFetch = root
                .fetch(ParkingLotRatingEntity_.parkingLotEntity);
        Join<ParkingLotRatingEntity, ParkingLotEntity> parkingLotEntityJoin =
                (Join<ParkingLotRatingEntity, ParkingLotEntity>) parkingLotEntityFetch;

        parkingLotEntityFetch.fetch(ParkingLotEntity_.parkingLotTypeEntity);
        parkingLotEntityFetch.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        parkingLotEntityFetch.fetch(ParkingLotEntity_.parkingLotInformationEntity);

        TypedQuery<ParkingLotRatingEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.equal(parkingLotEntityJoin.get(BaseEntity_.id), parkingLotId))
                        .orderBy(sortLastUpdatedAsc
                                ? criteriaBuilder.asc(root.get(ParkingLotRatingEntity_.lastUpdated))
                                : criteriaBuilder.desc(root.get(ParkingLotRatingEntity_.lastUpdated))));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotRatingEntity> getAllRatingsOfParkingLot(@NotNull Long parkingLotId,
                                                                  @NotNull @Range(min = 1L, max = 5L) Integer rating,
                                                                  boolean sortLastUpdatedAsc,
                                                                  @NotNull @Max(20L) Integer nRow,
                                                                  @NotNull Integer pageNumber) {

        CriteriaQuery<ParkingLotRatingEntity> query = criteriaBuilder.createQuery(ParkingLotRatingEntity.class);
        Root<ParkingLotRatingEntity> root = query.from(ParkingLotRatingEntity.class);

        Fetch<ParkingLotRatingEntity, ParkingLotEntity> parkingLotEntityFetch = root
                .fetch(ParkingLotRatingEntity_.parkingLotEntity);
        Join<ParkingLotRatingEntity, ParkingLotEntity> parkingLotEntityJoin =
                (Join<ParkingLotRatingEntity, ParkingLotEntity>) parkingLotEntityFetch;

        parkingLotEntityFetch.fetch(ParkingLotEntity_.parkingLotTypeEntity);
        parkingLotEntityFetch.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        parkingLotEntityFetch.fetch(ParkingLotEntity_.parkingLotInformationEntity);

        TypedQuery<ParkingLotRatingEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(parkingLotEntityJoin.get(BaseEntity_.id), parkingLotId),
                                criteriaBuilder.equal(root.get(ParkingLotRatingEntity_.rating), rating)))
                        .orderBy(sortLastUpdatedAsc
                                ? criteriaBuilder.asc(root.get(ParkingLotRatingEntity_.lastUpdated))
                                : criteriaBuilder.desc(root.get(ParkingLotRatingEntity_.lastUpdated))));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }
}