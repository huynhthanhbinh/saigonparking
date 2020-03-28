package com.bht.parkingmap.appserver.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.appserver.mapper.UserMapperExt;

import lombok.AllArgsConstructor;

/**
 *
 * User Mapper Extension
 * this class implement override methods of UserMapper
 *
 * Note that Ext class in declared as final non-public
 * in order to hide this class from outside of mapper package
 * It is expected to be seen only by mapper class, here is UserMapper
 *
 * for using repository inside Component class,
 * we need to {@code @Autowired} it by Spring Dependency Injection
 * we can achieve that easily
 * by using {@code @Setter(onMethod = @__(@Autowired)} for class level like below
 *
 * we cannot use {@code @AllArgsConstructor} for class level,
 * because these repository/injected fields are optional,
 * and it will conflict with {@code @Mapper @Component} bean
 * which will be initialized by NonArgsConstructor !!!!!!!!!
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserMapperExtImpl implements UserMapperExt {

}