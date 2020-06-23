package com.bht.saigonparking.service.parkinglot.mapper;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotType;
import com.bht.saigonparking.common.base.BaseBean;
import com.bht.saigonparking.service.parkinglot.configuration.AppConfiguration;
import com.bht.saigonparking.service.parkinglot.entity.ParkingLotTypeEntity;
import com.bht.saigonparking.service.parkinglot.repository.core.ParkingLotTypeRepository;
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

    private ParkingLotTypeRepository parkingLotTypeRepository;
    private static final BiMap<ParkingLotTypeEntity, ParkingLotType> PARKING_LOT_TYPE_BI_MAP = HashBiMap.create();
    private static final Map<Long, ParkingLotType> PARKING_LOT_TYPE_MAP = new HashMap<>();

    @Override
    public void initialize() {
        initParkingLotTypeBiMap();
        initParkingLotTypeMap();
    }

    @Named("toParkingLotType")
    public ParkingLotType toParkingLotType(@NotNull ParkingLotTypeEntity parkingLotTypeEntity) {
        return PARKING_LOT_TYPE_BI_MAP.get(parkingLotTypeEntity);
    }

    @Named("toParkingLotTypeEntity")
    public ParkingLotTypeEntity toParkingLotTypeEntity(@NotNull ParkingLotType parkingLotType) {
        return PARKING_LOT_TYPE_BI_MAP.inverse().get(parkingLotType);
    }


    @Named("toParkingLotTypeFromId")
    public ParkingLotType toParkingLotType(@NotNull Long parkingLotTypeId) {
        return PARKING_LOT_TYPE_MAP.get(parkingLotTypeId);
    }

    private void initParkingLotTypeBiMap() {
        PARKING_LOT_TYPE_BI_MAP.put(getParkingLotTypeByType("PRIVATE"), ParkingLotType.PRIVATE);
        PARKING_LOT_TYPE_BI_MAP.put(getParkingLotTypeByType("BUILDING"), ParkingLotType.BUILDING);
        PARKING_LOT_TYPE_BI_MAP.put(getParkingLotTypeByType("STREET"), ParkingLotType.STREET);
    }

    private void initParkingLotTypeMap() {
        PARKING_LOT_TYPE_MAP.put(PARKING_LOT_TYPE_BI_MAP.inverse().get(ParkingLotType.PRIVATE).getId(), ParkingLotType.PRIVATE);
        PARKING_LOT_TYPE_MAP.put(PARKING_LOT_TYPE_BI_MAP.inverse().get(ParkingLotType.BUILDING).getId(), ParkingLotType.BUILDING);
        PARKING_LOT_TYPE_MAP.put(PARKING_LOT_TYPE_BI_MAP.inverse().get(ParkingLotType.STREET).getId(), ParkingLotType.STREET);
    }

    private ParkingLotTypeEntity getParkingLotTypeByType(@NotEmpty String type) {
        return parkingLotTypeRepository.findByType(type).orElseThrow(EntityNotFoundException::new);
    }
}