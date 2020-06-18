package com.bht.saigonparking.service.user.repository.custom.impl;

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
import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.entity.UserRoleEntity;
import com.bht.saigonparking.service.user.repository.custom.UserRepositoryCustom;

/**
 *
 * @author bht
 */
@Repository
public class UserRepositoryCustomImpl extends BaseRepositoryCustom implements UserRepositoryCustom {

    @Override
    public Long countAll() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        return entityManager.createQuery(query
                .select(criteriaBuilder.count(root)))
                .getSingleResult();
    }

    @Override
    public Long countAll(@NotNull Boolean isActivated) {
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword) {
        return null;
    }

    @Override
    public Long countAll(@NotNull UserRoleEntity userRoleEntity) {
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull Boolean isActivated) {
        return null;
    }

    @Override
    public Long countAll(@NotNull UserRoleEntity userRoleEntity, @NotNull Boolean isActivated) {
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull UserRoleEntity userRoleEntity) {
        return null;
    }

    @Override
    public Long countAll(@NotEmpty String keyword, @NotNull UserRoleEntity userRoleEntity, @NotNull Boolean isActivated) {
        return null;
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);

        Root<UserEntity> root = query.from(UserEntity.class);
        root.fetch("userRoleEntity");

        TypedQuery<UserEntity> getAllQuery = entityManager
                .createQuery(query
                        .select(root)
                        .orderBy(criteriaBuilder.asc(root)));

        getAllQuery.setMaxResults(nRow);
        getAllQuery.setFirstResult(nRow * (pageNumber - 1));

        return getAllQuery.getResultList();
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull Boolean isActivated) {
        return Collections.emptyList();
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword) {
        return Collections.emptyList();
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull UserRoleEntity userRoleEntity) {
        return Collections.emptyList();
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull Boolean isActivated) {
        return Collections.emptyList();
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotNull UserRoleEntity userRoleEntity, @NotNull Boolean isActivated) {
        return Collections.emptyList();
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull UserRoleEntity userRoleEntity) {
        return Collections.emptyList();
    }

    @Override
    public List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber, @NotEmpty String keyword, @NotNull UserRoleEntity userRoleEntity, @NotNull Boolean isActivated) {
        return Collections.emptyList();
    }
}