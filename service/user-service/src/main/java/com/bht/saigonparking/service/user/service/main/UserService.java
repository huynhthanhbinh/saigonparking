package com.bht.saigonparking.service.user.service.main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;
import com.bht.saigonparking.service.user.entity.UserRoleEntity;

/**
 *
 * @author bht
 */
public interface UserService {

    Long countAll(@NotEmpty String keyword, boolean inactivatedOnly);

    Long countAll(@NotEmpty String keyword, boolean inactivatedOnly, @NotNull UserRoleEntity userRoleEntity);

    List<UserEntity> getAll(@NotNull Set<Long> userIdSet);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotEmpty String keyword,
                            boolean inactivatedOnly);

    List<UserEntity> getAll(@NotNull @Max(20L) Integer nRow,
                            @NotNull Integer pageNumber,
                            @NotEmpty String keyword,
                            boolean inactivatedOnly,
                            @NotNull UserRoleEntity userRoleEntity);

    UserEntity getUserById(@NotNull Long id);

    UserEntity getUserByUsername(@NotEmpty String username);

    CustomerEntity getCustomerById(@NotNull Long id);

    CustomerEntity getCustomerByUsername(@NotEmpty String username);

    Long createCustomer(@NotNull CustomerEntity customerEntity);

    void updateCustomer(@NotNull CustomerEntity customerEntity);

    void updateUserLastSignIn(@NotNull Long id, @NotNull Long timeInMillis);

    void activateUserWithId(@NotNull Long id);

    void deactivateUserWithId(@NotNull Long id);

    void updateUserPassword(@NotNull UserEntity userEntity, @NotEmpty String newPassword);

    void deleteUserById(@NotNull Long userId);

    void deleteMultiUserById(@NotNull Set<Long> userIdSet);

    Map<Long, String> mapToUsernameMap(@NotNull Set<Long> userIdList);

    Map<Long, Long> countAllUserGroupByRole();
}