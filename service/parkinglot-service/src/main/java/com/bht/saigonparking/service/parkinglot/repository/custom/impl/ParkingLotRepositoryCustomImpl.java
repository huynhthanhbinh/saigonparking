package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseEntity_;
import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity_;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotInformationEntity_;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
@SuppressWarnings("unchecked")
public class ParkingLotRepositoryCustomImpl extends BaseRepositoryCustom implements ParkingLotRepositoryCustom {

    @Override
    public Long countAll() {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root)))
                .getSingleResult();
    }

    @Override
    public Long countAll(boolean isAvailable) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(isAvailable
                        ? criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                        criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                        criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                        : criteriaBuilder.or(
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                        criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                        criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime()))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin = root
                .join(ParkingLotEntity_.parkingLotInformationEntity, JoinType.LEFT);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.or(
                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword)))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin = root
                .join(ParkingLotEntity_.parkingLotTypeEntity, JoinType.LEFT);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId())))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword, boolean isAvailable) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin = root
                .join(ParkingLotEntity_.parkingLotInformationEntity, JoinType.LEFT);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        isAvailable
                                ? criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                                criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                                : criteriaBuilder.or(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                                criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                                criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime())),
                        criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword))))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity, boolean isAvailable) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin = root
                .join(ParkingLotEntity_.parkingLotTypeEntity, JoinType.LEFT);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId()),
                        isAvailable
                                ? criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                                criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                                : criteriaBuilder.or(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                                criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                                criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime())))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin = root
                .join(ParkingLotEntity_.parkingLotTypeEntity, JoinType.LEFT);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin = root
                .join(ParkingLotEntity_.parkingLotInformationEntity, JoinType.LEFT);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId()),
                        criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword))))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, boolean isAvailable) {

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin = root
                .join(ParkingLotEntity_.parkingLotTypeEntity, JoinType.LEFT);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin = root
                .join(ParkingLotEntity_.parkingLotInformationEntity, JoinType.LEFT);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId()),
                        isAvailable
                                ? criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                                criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                                : criteriaBuilder.or(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                                criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                                criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime())),
                        criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword))))))
                .getSingleResult();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.parkingLotTypeEntity);
        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        root.fetch(ParkingLotEntity_.parkingLotInformationEntity);

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, boolean isAvailable) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        root.fetch(ParkingLotEntity_.parkingLotTypeEntity);
        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        root.fetch(ParkingLotEntity_.parkingLotInformationEntity);

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(isAvailable
                                ? criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                                criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                                : criteriaBuilder.or(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                                criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                                criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime())))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.parkingLotTypeEntity);
        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotInformationEntity);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        root.fetch(ParkingLotEntity_.parkingLotInformationEntity);
        Fetch<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotTypeEntity);
        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin =
                (Join<ParkingLotEntity, ParkingLotTypeEntity>) parkingLotTypeEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId()))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, boolean isAvailable) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        root.fetch(ParkingLotEntity_.parkingLotTypeEntity);
        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotInformationEntity);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                isAvailable
                                        ? criteriaBuilder.and(
                                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                                        criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                                        criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                                        : criteriaBuilder.or(
                                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                                        criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                                        criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime())),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword)))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, boolean isAvailable) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        root.fetch(ParkingLotEntity_.parkingLotInformationEntity);
        Fetch<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotTypeEntity);
        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin =
                (Join<ParkingLotEntity, ParkingLotTypeEntity>) parkingLotTypeEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId()),
                                isAvailable
                                        ? criteriaBuilder.and(
                                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                                        criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                                        criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                                        : criteriaBuilder.or(
                                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                                        criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                                        criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime()))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        Fetch<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotTypeEntity);
        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin =
                (Join<ParkingLotEntity, ParkingLotTypeEntity>) parkingLotTypeEntityFetch;
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotInformationEntity);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId()),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword)))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, boolean isAvailable) {

        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        Time currentTime = Time.valueOf(LocalTime.now());

        root.fetch(ParkingLotEntity_.parkingLotLimitEntity);
        Fetch<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotTypeEntity);
        Join<ParkingLotEntity, ParkingLotTypeEntity> parkingLotTypeEntityJoin =
                (Join<ParkingLotEntity, ParkingLotTypeEntity>) parkingLotTypeEntityFetch;
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.parkingLotInformationEntity);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(parkingLotTypeEntityJoin.get(BaseEntity_.id), parkingLotTypeEntity.getId()),
                                isAvailable
                                        ? criteriaBuilder.and(
                                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), true),
                                        criteriaBuilder.lessThanOrEqualTo(root.get(ParkingLotEntity_.openingHour), currentTime),
                                        criteriaBuilder.greaterThanOrEqualTo(root.get(ParkingLotEntity_.closingHour), currentTime))
                                        : criteriaBuilder.or(
                                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), false),
                                        criteriaBuilder.greaterThan(root.get(ParkingLotEntity_.openingHour), criteriaBuilder.currentTime()),
                                        criteriaBuilder.lessThan(root.get(ParkingLotEntity_.closingHour), criteriaBuilder.currentTime())),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.name), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.address), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.phone), convertKeyword(keyword)))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }
}