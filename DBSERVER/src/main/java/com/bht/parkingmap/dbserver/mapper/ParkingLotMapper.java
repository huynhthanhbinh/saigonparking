package com.bht.parkingmap.dbserver.mapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLot;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotResult;
import com.bht.parkingmap.dbserver.configuration.AppConfiguration;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.dbserver.entity.parkinglot.ParkingLotInformationEntity;

import lombok.Setter;

/**
 *
 * Mapper class for parking-lot entities and its families
 * Mapper is used for mapping objects from different layers
 * For example here is: map Entity obj to DTO obj and vice versa
 *
 * @author bht
 */
@Component
@DependsOn("parkingLotMapperExt")
@SuppressWarnings("UnmappedTargetProperties")
@Setter(onMethod = @__(@Autowired))
@Mapper(componentModel = "spring",
        implementationPackage = AppConfiguration.BASE_PACKAGE_SERVER + ".mapper.impl",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class})
public abstract class ParkingLotMapper {

    private ParkingLotMapperExt parkingLotMapperExt;


    @Named("toParkingLotResultWithoutName")
    @Mapping(target = "id", expression = "java(parkingLotWithoutNameTuple.get(0, java.math.BigInteger.class).longValue())")
    @Mapping(target = "type", expression = "java(enumMapper.toParkingLotType(parkingLotWithoutNameTuple.get(1, java.math.BigInteger.class).longValue()))")
    @Mapping(target = "latitude", expression = "java(parkingLotWithoutNameTuple.get(2, Double.class))")
    @Mapping(target = "longitude", expression = "java(parkingLotWithoutNameTuple.get(3, Double.class))")
    @Mapping(target = "availableSlot", expression = "java(parkingLotWithoutNameTuple.get(4, Short.class))")
    @Mapping(target = "totalSlot", expression = "java(parkingLotWithoutNameTuple.get(5, Short.class))")
    public abstract ParkingLotResult toParkingLotResultWithoutName(@NotNull Tuple parkingLotWithoutNameTuple);


    @Named("toParkingLotResultWithName")
    @Mapping(target = "id", expression = "java(parkingLotWithNameTuple.get(0, java.math.BigInteger.class).longValue())")
    @Mapping(target = "name", expression = "java(parkingLotWithNameTuple.get(1, String.class))")
    @Mapping(target = "type", expression = "java(enumMapper.toParkingLotType(parkingLotWithNameTuple.get(2, java.math.BigInteger.class).longValue()))")
    @Mapping(target = "latitude", expression = "java(parkingLotWithNameTuple.get(3, Double.class))")
    @Mapping(target = "longitude", expression = "java(parkingLotWithNameTuple.get(4, Double.class))")
    @Mapping(target = "availableSlot", expression = "java(parkingLotWithNameTuple.get(5, Short.class))")
    @Mapping(target = "totalSlot", expression = "java(parkingLotWithNameTuple.get(6, Short.class))")
    public abstract ParkingLotResult toParkingLotResultWithName(@NotNull Tuple parkingLotWithNameTuple);


    @Named("toParkingLotInformation")
    @Mapping(target = "name", source = "name", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "address", source = "address", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "phone", source = "phone", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "ratingAverage", source = "ratingAverage", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    @Mapping(target = "numberOfRating", source = "NRating", defaultExpression = "java(customizedMapper.DEFAULT_SHORT_VALUE)")
    @Mapping(target = "imageData", source = "id", qualifiedByName = "toEncodedParkingLotImage", defaultExpression = "java(customizedMapper.DEFAULT_BYTE_STRING_VALUE)")
    @Mapping(target = "version", source = "version", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    public abstract ParkingLotInformation toParkingLotInformation(@NotNull ParkingLotInformationEntity parkingLotInformationEntity) throws IOException;


    @Named("toParkingLot")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "information", source = "parkingLotInformationEntity", qualifiedByName = "toParkingLotInformation", defaultExpression = "java(customizedMapper.DEFAULT_PARKING_LOT_INFORMATION)")
    @Mapping(target = "type", source = "parkingLotTypeEntity.id", qualifiedByName = "toParkingLotType", defaultExpression = "java(customizedMapper.DEFAULT_PARKING_LOT_TYPE)")
    @Mapping(target = "latitude", source = "latitude", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    @Mapping(target = "longitude", source = "longitude", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    @Mapping(target = "openingHour", source = "openingHour", qualifiedByName = "toTimeString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "closingHour", source = "closingHour", qualifiedByName = "toTimeString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "availableSlot", source = "parkingLotLimitEntity.availableSlot", defaultExpression = "java(customizedMapper.DEFAULT_SHORT_VALUE)")
    @Mapping(target = "totalSlot", source = "parkingLotLimitEntity.totalSlot", defaultExpression = "java(customizedMapper.DEFAULT_SHORT_VALUE)")
    @Mapping(target = "version", source = "version", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    public abstract ParkingLot toParkingLot(@NotNull ParkingLotEntity parkingLotEntity);


    @Named("toParkingLotEntity")
    public final ParkingLotEntity toParkingLotEntity(@NotNull ParkingLot parkingLot) {
        return parkingLotMapperExt.toParkingLotEntity(parkingLot);
    }


    @Named("toParkingLotResultListWithoutName")
    public final List<ParkingLotResult> toParkingLotResultListWithoutName(@NotNull List<Tuple> parkingLotWithoutNameTupleList) {
        return parkingLotWithoutNameTupleList.stream().map(this::toParkingLotResultWithoutName).collect(Collectors.toList());
    }


    @Named("toParkingLotResultListWithName")
    public final List<ParkingLotResult> toParkingLotResultListWithName(@NotNull List<Tuple> parkingLotWithNameTupleList) {
        return parkingLotWithNameTupleList.stream().map(this::toParkingLotResultWithName).collect(Collectors.toList());
    }


    @Named("toParkingLotList")
    public final List<ParkingLot> toParkingLotList(@NotNull List<ParkingLotEntity> parkingLotEntityList) {
        return parkingLotEntityList.stream().map(this::toParkingLot).collect(Collectors.toList());
    }
}