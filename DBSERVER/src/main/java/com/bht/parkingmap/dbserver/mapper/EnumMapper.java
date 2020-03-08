package com.bht.parkingmap.dbserver.mapper;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotType;
import com.bht.parkingmap.api.proto.user.UserRole;
import com.bht.parkingmap.dbserver.base.BaseBean;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotTypeEntity;
import com.bht.parkingmap.dbserver.entity.user.UserRoleEntity;
import com.bht.parkingmap.dbserver.repository.parkinglot.ParkingLotTypeRepository;
import com.bht.parkingmap.dbserver.repository.user.UserRoleRepository;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class EnumMapper implements BaseBean {

    private final UserRoleRepository userRoleRepository;
    private final ParkingLotTypeRepository parkingLotTypeRepository;

    private static final Map<Long, UserRole> USER_ROLE_MAP = new HashMap<>(4);
    private static final Map<Long, ParkingLotType> PARKING_LOT_TYPE_MAP = new HashMap<>(4);

    private static final BiMap<UserRoleEntity, UserRole> USER_ROLE_BI_MAP = HashBiMap.create(4);
    private static final BiMap<ParkingLotTypeEntity, ParkingLotType> PARKING_LOT_TYPE_BI_MAP = HashBiMap.create(4);

    @Override
    public void initialize() {
        initUserRoleMap();
        initUserRoleBiMap();
        initParkingLotTypeMap();
        initParkingLotTypeBiMap();
    }

    @Named("toUserRoleFromId")
    public UserRole toUserRole(Long userRoleId) {
        return USER_ROLE_MAP.get(userRoleId);
    }

    @Named("toUserRole")
    public UserRole toUserRole(UserRoleEntity userRoleEntity) {
        return USER_ROLE_BI_MAP.get(userRoleEntity);
    }

    @Named("toUserRoleEntity")
    public UserRoleEntity toUserRoleEntity(UserRole userRole) {
        return USER_ROLE_BI_MAP.inverse().get(userRole);
    }

    @Named("toParkingLotTypeFromId")
    public ParkingLotType toParkingLotType(Long parkingLotTypeId) {
        return PARKING_LOT_TYPE_MAP.get(parkingLotTypeId);
    }

    @Named("toParkingLotType")
    public ParkingLotType toParkingLotType(ParkingLotTypeEntity parkingLotTypeEntity) {
        return PARKING_LOT_TYPE_BI_MAP.get(parkingLotTypeEntity);
    }

    @Named("toParkingLotTypeEntity")
    public ParkingLotTypeEntity toParkingLotTypeEntity(ParkingLotType parkingLotType) {
        return PARKING_LOT_TYPE_BI_MAP.inverse().get(parkingLotType);
    }

    // initialize ======================================================================================================

    private void initUserRoleMap() {
        USER_ROLE_MAP.put(0L, UserRole.UNRECOGNIZED);
        USER_ROLE_MAP.put(1L, UserRole.ADMIN);
        USER_ROLE_MAP.put(2L, UserRole.CUSTOMER);
        USER_ROLE_MAP.put(3L, UserRole.PARKING_LOT_EMPLOYEE);
    }

    private void initUserRoleBiMap() {
        USER_ROLE_BI_MAP.put(userRoleRepository.getOne(0L), UserRole.UNRECOGNIZED);
        USER_ROLE_BI_MAP.put(userRoleRepository.getOne(1L), UserRole.ADMIN);
        USER_ROLE_BI_MAP.put(userRoleRepository.getOne(2L), UserRole.CUSTOMER);
        USER_ROLE_BI_MAP.put(userRoleRepository.getOne(3L), UserRole.PARKING_LOT_EMPLOYEE);
    }

    private void initParkingLotTypeMap() {
        PARKING_LOT_TYPE_MAP.put(0L, ParkingLotType.UNRECOGNIZED);
        PARKING_LOT_TYPE_MAP.put(1L, ParkingLotType.PRIVATE);
        PARKING_LOT_TYPE_MAP.put(2L, ParkingLotType.BUILDING);
        PARKING_LOT_TYPE_MAP.put(3L, ParkingLotType.STREET);
    }

    private void initParkingLotTypeBiMap() {
        PARKING_LOT_TYPE_BI_MAP.put(parkingLotTypeRepository.getOne(0L), ParkingLotType.UNRECOGNIZED);
        PARKING_LOT_TYPE_BI_MAP.put(parkingLotTypeRepository.getOne(1L), ParkingLotType.PRIVATE);
        PARKING_LOT_TYPE_BI_MAP.put(parkingLotTypeRepository.getOne(2L), ParkingLotType.BUILDING);
        PARKING_LOT_TYPE_BI_MAP.put(parkingLotTypeRepository.getOne(3L), ParkingLotType.STREET);
    }
}