package com.bht.parkingmap.dbserver.mapper;

import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.user.Customer;
import com.bht.parkingmap.api.proto.user.ParkingLotEmployee;
import com.bht.parkingmap.api.proto.user.User;
import com.bht.parkingmap.dbserver.entity.user.CustomerEntity;
import com.bht.parkingmap.dbserver.entity.user.ParkingLotEmployeeEntity;
import com.bht.parkingmap.dbserver.entity.user.UserEntity;

import lombok.Setter;

/**
 *
 * @author bht
 */
@Component
@DependsOn("userMapperExt")
@SuppressWarnings("UnmappedTargetProperties")
@Setter(onMethod = @__(@Autowired))
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class})
public abstract class UserMapper {

    private UserMapperExt userMapperExt;


    @Named("toUser")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "role", source = "userRoleEntity", qualifiedByName = "toUserRole", defaultExpression = "java(customizedMapper.DEFAULT_USER_ROLE)")
    @Mapping(target = "username", source = "username", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "password", source = "password", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "email", source = "email", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastSignIn", source = "lastSignIn", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "version", source = "version", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    public abstract User toUser(@NotNull UserEntity userEntity);


    @Named("toCustomer")
    @Mapping(target = "userInfo", source = "customerEntity", qualifiedByName = "toUser", defaultExpression = "java(customizedMapper.DEFAULT_USER)")
    @Mapping(target = "firstName", source = "firstName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastName", source = "lastName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "phone", source = "phone", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    public abstract Customer toCustomer(@NotNull CustomerEntity customerEntity);


    @Named("toCustomerWithoutUserInfo")
    @Mapping(target = "userInfo", expression = "java(customizedMapper.DEFAULT_USER)")
    @Mapping(target = "firstName", source = "firstName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastName", source = "lastName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "phone", source = "phone", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    public abstract Customer toCustomerWithoutUserInfo(@NotNull CustomerEntity customerEntity);


    @Named("toParkingLotEmployee")
    @Mapping(target = "userInfo", source = "parkingLotEmployeeEntity", qualifiedByName = "toUser", defaultExpression = "java(customizedMapper.DEFAULT_USER)")
    @Mapping(target = "parkingLotId", source = "parkingLotEntity.id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    public abstract ParkingLotEmployee toParkingLotEmployee(@NotNull ParkingLotEmployeeEntity parkingLotEmployeeEntity);
}