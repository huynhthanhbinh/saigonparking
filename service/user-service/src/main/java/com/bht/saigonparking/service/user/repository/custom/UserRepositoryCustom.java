package com.bht.saigonparking.service.user.repository.custom;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.user.entity.UserEntity;

/**
 *
 * @author bht
 */
public interface UserRepositoryCustom {

    Long countAll();

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow, @NotNull Integer pageNumber);
}