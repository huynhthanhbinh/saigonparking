package com.bht.saigonparking.service.user.repository.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bht.saigonparking.service.user.base.BaseRepositoryCustom;
import com.bht.saigonparking.service.user.repository.custom.UserRepositoryCustom;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryCustomImpl extends BaseRepositoryCustom implements UserRepositoryCustom {
}