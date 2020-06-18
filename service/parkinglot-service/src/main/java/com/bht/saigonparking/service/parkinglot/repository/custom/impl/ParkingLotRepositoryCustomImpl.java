package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root)))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable)))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.or(
                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword)))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity)))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable),
                        criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword))))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable),
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity),
                        criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword))))))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root))
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable),
                        criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity),
                        criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword))))))
                .getSingleResult();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.or(
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                                criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword)))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable),
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity)))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;

        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword)))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);
        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);

        root.fetch(ParkingLotEntity_.PARKING_LOT_TYPE_ENTITY);
        root.fetch(ParkingLotEntity_.PARKING_LOT_LIMIT_ENTITY);
        Fetch<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityFetch = root
                .fetch(ParkingLotEntity_.PARKING_LOT_INFORMATION_ENTITY);
        Join<ParkingLotEntity, ParkingLotInformationEntity> parkingLotInformationEntityJoin =
                (Join<ParkingLotEntity, ParkingLotInformationEntity>) parkingLotInformationEntityFetch;


        TypedQuery<ParkingLotEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.isAvailable), isAvailable),
                                criteriaBuilder.equal(root.get(ParkingLotEntity_.parkingLotTypeEntity), parkingLotTypeEntity),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.NAME), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.ADDRESS), convertKeyword(keyword)),
                                        criteriaBuilder.like(parkingLotInformationEntityJoin.get(ParkingLotInformationEntity_.PHONE), convertKeyword(keyword)))))
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }
}