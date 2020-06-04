package com.bht.saigonparking.service.user.mapper;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.user.Customer;
import com.bht.saigonparking.api.grpc.user.ParkingLotEmployee;
import com.bht.saigonparking.api.grpc.user.User;
import com.bht.saigonparking.service.user.configuration.AppConfiguration;
import com.bht.saigonparking.service.user.entity.CustomerEntity;
import com.bht.saigonparking.service.user.entity.ParkingLotEmployeeEntity;
import com.bht.saigonparking.service.user.entity.UserEntity;

/**
 *
 * Mapper class for user entities and its families
 * Mapper is used for mapping objects from different layers
 * For example here is: map Entity obj to DTO obj and vice versa
 *
 * @author bht
 */
@Component
@SuppressWarnings("UnmappedTargetProperties")
@Mapper(componentModel = "spring",
        implementationPackage = AppConfiguration.BASE_PACKAGE + ".mapper.impl",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class})
public interface UserMapper {

    @Named("toUser")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "role", source = "userRoleEntity", qualifiedByName = "toUserRole", defaultExpression = "java(customizedMapper.DEFAULT_USER_ROLE)")
    @Mapping(target = "username", source = "username", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "password", source = "password", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "email", source = "email", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "isActivated", source = "isActivated", defaultExpression = "java(customizedMapper.DEFAULT_BOOL_VALUE)")
    @Mapping(target = "lastSignIn", source = "lastSignIn", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "version", source = "version", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    User toUser(@NotNull UserEntity userEntity);


    @Named("toUserList")
    default List<User> toUserList(@NotNull List<UserEntity> userEntityList) {
        return userEntityList.stream().map(this::toUser).collect(Collectors.toList());
    }


    @Named("toCustomer")
    @Mapping(target = "userInfo", source = "customerEntity", qualifiedByName = "toUser", defaultExpression = "java(customizedMapper.DEFAULT_USER)")
    @Mapping(target = "firstName", source = "firstName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastName", source = "lastName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "phone", source = "phone", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    Customer toCustomer(@NotNull CustomerEntity customerEntity);


    @Named("toCustomerWithoutUserInfo")
    @Mapping(target = "userInfo", ignore = true)
    @Mapping(target = "firstName", source = "firstName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastName", source = "lastName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "phone", source = "phone", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    Customer toCustomerWithoutUserInfo(@NotNull CustomerEntity customerEntity);


    @Named("toParkingLotEmployee")
    @Mapping(target = "userInfo", source = "parkingLotEmployeeEntity", qualifiedByName = "toUser", defaultExpression = "java(customizedMapper.DEFAULT_USER)")
    @Mapping(target = "parkingLotId", source = "parkingLotId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    ParkingLotEmployee toParkingLotEmployee(@NotNull ParkingLotEmployeeEntity parkingLotEmployeeEntity);
}