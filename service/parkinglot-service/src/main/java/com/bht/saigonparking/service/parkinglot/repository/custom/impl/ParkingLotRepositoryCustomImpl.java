package com.bht.saigonparking.service.parkinglot.repository.custom.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

import com.bht.saigonparking.common.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotEntity;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;
import com.bht.saigonparking.service.parkinglot.repository.custom.ParkingLotRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
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
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword) {
        return null;
    }

    @Override
    public Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity) {
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull Boolean isAvailable) {
        return null;
    }

    @Override
    public Long countAll(@NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {
        return null;
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ParkingLotEntity> query = criteriaBuilder.createQuery(ParkingLotEntity.class);

        Root<ParkingLotEntity> root = query.from(ParkingLotEntity.class);
        root.fetch("parkingLotTypeEntity");
        root.fetch("parkingLotLimitEntity");
        root.fetch("parkingLotInformationEntity");

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
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword) {
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull Boolean isAvailable) {
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity) {
        return Collections.emptyList();
    }

    @Override
    public List<ParkingLotEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull ParkingLotTypeEntity parkingLotTypeEntity, @NotNull Boolean isAvailable) {
        return Collections.emptyList();
    }
}