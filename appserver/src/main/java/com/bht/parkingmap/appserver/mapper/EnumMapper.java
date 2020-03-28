package com.bht.parkingmap.appserver.mapper;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotType;
import com.bht.parkingmap.api.proto.user.UserRole;
import com.bht.parkingmap.appserver.base.BaseBean;
import com.bht.parkingmap.appserver.configuration.AppConfiguration;
import com.bht.parkingmap.appserver.entity.user.UserRoleEntity;
import com.bht.parkingmap.appserver.repository.user.UserRoleRepository;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.Setter;

/**
 *
 * this class is self-customized mapper for all enums, include:
 *      + UserRole:       3 role --> ADMIN, CUSTOMER, PARKING_LOT_EMPLOYEE
 *      + ParkingLotType: 3 type --> PRIVATE, BUILDING, STREET
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
@Setter(onMethod = @__(@Autowired))
@Mapper(componentModel = "spring",
        implementationPackage = AppConfiguration.BASE_PACKAGE + ".mapper.impl",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class EnumMapper implements BaseBean {

    private UserRoleRepository userRoleRepository;
    private static final Map<Long, ParkingLotType> PARKING_LOT_TYPE_MAP = new HashMap<>();
    private static final BiMap<UserRoleEntity, UserRole> USER_ROLE_BI_MAP = HashBiMap.create();

    @Override
    public void initialize() {
        initUserRoleBiMap();
        initParkingLotTypeMap();
    }

    @Named("toUserRole")
    public UserRole toUserRole(@NotNull UserRoleEntity userRoleEntity) {
        return USER_ROLE_BI_MAP.get(userRoleEntity);
    }

    @Named("toUserRoleEntity")
    public UserRoleEntity toUserRoleEntity(@NotNull UserRole userRole) {
        return USER_ROLE_BI_MAP.inverse().get(userRole);
    }

    @Named("toParkingLotTypeFromId")
    public ParkingLotType toParkingLotType(@NotNull Long parkingLotTypeId) {
        return PARKING_LOT_TYPE_MAP.get(parkingLotTypeId);
    }

    // initialize ======================================================================================================

    private void initUserRoleBiMap() {
        USER_ROLE_BI_MAP.put(userRoleRepository.getOne(1L), UserRole.ADMIN);
        USER_ROLE_BI_MAP.put(userRoleRepository.getOne(2L), UserRole.CUSTOMER);
        USER_ROLE_BI_MAP.put(userRoleRepository.getOne(3L), UserRole.PARKING_LOT_EMPLOYEE);
    }

    private void initParkingLotTypeMap() {
        PARKING_LOT_TYPE_MAP.put(1L, ParkingLotType.PRIVATE);
        PARKING_LOT_TYPE_MAP.put(2L, ParkingLotType.BUILDING);
        PARKING_LOT_TYPE_MAP.put(3L, ParkingLotType.STREET);
    }
}