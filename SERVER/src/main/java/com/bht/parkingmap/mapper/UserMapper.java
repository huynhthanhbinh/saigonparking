package com.bht.parkingmap.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.user.Customer;
import com.bht.parkingmap.api.proto.user.ParkingLotEmployee;
import com.bht.parkingmap.api.proto.user.User;
import com.bht.parkingmap.entity.user.CustomerEntity;
import com.bht.parkingmap.entity.user.ParkingLotEmployeeEntity;
import com.bht.parkingmap.entity.user.UserEntity;

/**
 *
 * @author bht
 */
@Component
@SuppressWarnings("UnmappedTargetProperties")
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class})
public interface UserMapper {

    @Named("toUser")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "role", source = "userRoleEntity.id", qualifiedByName = "toUserRole", defaultExpression = "java(customizedMapper.DEFAULT_USER_ROLE)")
    @Mapping(target = "username", source = "username", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "password", source = "password", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "email", source = "email", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastSignIn", source = "lastSignIn", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    User toUser(UserEntity userEntity);


    @Named("toCustomer")
    @Mapping(target = "userInfo", source = "customerEntity", qualifiedByName = "toUser")
    @Mapping(target = "firstName", source = "firstName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastName", source = "lastName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "phone", source = "phone", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    Customer toCustomer(CustomerEntity customerEntity);


    @Named("toParkingLotEmployee")
    @Mapping(target = "userInfo", source = "parkingLotEmployeeEntity", qualifiedByName = "toUser")
    @Mapping(target = "parkingLotId", source = "parkingLotEntity.id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    ParkingLotEmployee toParkingLotEmployee(ParkingLotEmployeeEntity parkingLotEmployeeEntity);
}