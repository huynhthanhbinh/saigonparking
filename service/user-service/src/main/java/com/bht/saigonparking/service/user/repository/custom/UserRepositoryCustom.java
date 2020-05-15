package com.bht.saigonparking.service.user.repository.custom;

import javax.validation.constraints.NotNull;

/**
 *
 * @author bht
 */
public interface UserRepositoryCustom {

    void updateUserLastSignIn(@NotNull Long id);
}