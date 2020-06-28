package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseEntity_;
import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity_;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotInformationRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public class ParkingLotInformationRepositoryCustomImpl extends BaseRepositoryCustom implements ParkingLotInformationRepositoryCustom {

    @Override
    public Long countAllHasRatings() {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotInformationEntity> root = query.from(ParkingLotInformationEntity.class);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.greaterThan(root.get(ParkingLotInformationEntity_.nRating), (short) 0)))
                .getSingleResult();
    }

    @Override
    public Long countAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                   @NotNull @Range(max = 5L) Integer upperBound) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotInformationEntity> root = query.from(ParkingLotInformationEntity.class);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get(ParkingLotInformationEntity_.nRating), (short) 0),
                        criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotInformationEntity_.ratingAverage), (double) lowerBound),
                        criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotInformationEntity_.ratingAverage), (double) upperBound))))
                .getSingleResult();
    }

    @Override
    public List<Tuple> getAllHasRatings(boolean sortRatingAsc,
                                        @NotNull @Max(20L) Integer nRow,
                                        @NotNull Integer pageNumber) {

        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<ParkingLotInformationEntity> root = query.from(ParkingLotInformationEntity.class);

        TypedQuery<Tuple> getAllQuery = entityManager
                .createQuery(query
                        .multiselect(
                                root.get(BaseEntity_.id),
                                root.get(ParkingLotInformationEntity_.name),
                                root.get(ParkingLotInformationEntity_.nRating),
                                root.get(ParkingLotInformationEntity_.ratingAverage))
                        .where(criteriaBuilder.greaterThan(root.get(ParkingLotInformationEntity_.nRating), (short) 0))
                        .orderBy(sortRatingAsc
                                ? criteriaBuilder.asc(root.get(ParkingLotInformationEntity_.ratingAverage))
                                : criteriaBuilder.desc(root.get(ParkingLotInformationEntity_.ratingAverage))));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<Tuple> getAllHasRatings(@NotNull @Range(max = 5L) Integer lowerBound,
                                        @NotNull @Range(max = 5L) Integer upperBound,
                                        boolean sortRatingAsc,
                                        @NotNull @Max(20L) Integer nRow,
                                        @NotNull Integer pageNumber) {

        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        Root<ParkingLotInformationEntity> root = query.from(ParkingLotInformationEntity.class);

        TypedQuery<Tuple> getAllQuery = entityManager
                .createQuery(query
                        .multiselect(
                                root.get(BaseEntity_.id),
                                root.get(ParkingLotInformationEntity_.name),
                                root.get(ParkingLotInformationEntity_.nRating),
                                root.get(ParkingLotInformationEntity_.ratingAverage))
                        .where(criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get(ParkingLotInformationEntity_.nRating), (short) 0),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotInformationEntity_.ratingAverage), (double) lowerBound),
                                criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotInformationEntity_.ratingAverage), (double) upperBound)))
                        .orderBy(sortRatingAsc
                                ? criteriaBuilder.asc(root.get(ParkingLotInformationEntity_.ratingAverage))
                                : criteriaBuilder.desc(root.get(ParkingLotInformationEntity_.ratingAverage))));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }
}