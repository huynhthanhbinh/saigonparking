package com.bht.saigonparking.service.user.repository.custom;

import java.util.List;

import javax.persistence.Tuple;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.entity.UserRoleEntity;

/**
 *
 * @author bht
 */
public interface UserRepositoryCustom {

    List<Tuple> countAllUserGroupByRole();

    Long countAll();

    Long countAll(@NotNull Boolean isActivated);

    Long countAll(@NotEmpty String keyword);

    Long countAll(@NotNull UserRoleEntity userRoleEntity);

    Long countAll(@NotEmpty String keyword, @NotNull Boolean isActivated);

    Long countAll(@NotNull UserRoleEntity userRoleEntity, @NotNull Boolean isActivated);

    Long countAll(@NotEmpty String keyword, @NotNull UserRoleEntity userRoleEntity);

    Long countAll(@NotEmpty String keyword, @NotNull UserRoleEntity userRoleEntity, @NotNull Boolean isActivated);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotNull Boolean isActivated);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotEmpty String keyword);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotNull UserRoleEntity userRoleEntity);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotEmpty String keyword,
                            @NotNull Boolean isActivated);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotNull UserRoleEntity userRoleEntity,
                            @NotNull Boolean isActivated);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotEmpty String keyword,
                            @NotNull UserRoleEntity userRoleEntity);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotEmpty String keyword,
                            @NotNull UserRoleEntity userRoleEntity,
                            @NotNull Boolean isActivated);
}