package com.bht.parkingmap.mapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLot;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;
import com.bht.parkingmap.entity.parkinglot.ParkingLotInformationEntity;

/**
 *
 * @author bht
 */
@Component
@SuppressWarnings("UnmappedTargetProperties")
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class})
public interface ParkingLotMapper {

    @Named("toParkingLotWithDistance")
    @Mapping(target = "id", expression = "java(parkingLotDistanceTuple.get(0, java.math.BigInteger.class).longValue())")
    @Mapping(target = "name", expression = "java(parkingLotDistanceTuple.get(1, String.class))")
    @Mapping(target = "type", expression = "java(enumMapper.toParkingLotType(parkingLotDistanceTuple.get(2, Byte.class).intValue()))")
    @Mapping(target = "latitude", expression = "java(parkingLotDistanceTuple.get(3, Double.class))")
    @Mapping(target = "longitude", expression = "java(parkingLotDistanceTuple.get(4, Double.class))")
    @Mapping(target = "distance", expression = "java(parkingLotDistanceTuple.get(5, Double.class))")
    ParkingLot toParkingLotWithDistance(Tuple parkingLotDistanceTuple);


    @Named("toParkingLotListWithDistance")
    default List<ParkingLot> toParkingLotListWithDistance(List<Tuple> parkingLotDistanceTupleList) {
        return parkingLotDistanceTupleList.stream().map(this::toParkingLotWithDistance).collect(Collectors.toList());
    }


    @Named("toParkingLot")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "type", source = "parkingLotTypeEntity.id", qualifiedByName = "toParkingLotType", defaultExpression = "java(customizedMapper.DEFAULT_PARKING_LOT_TYPE)")
    @Mapping(target = "latitude", source = "latitude", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    @Mapping(target = "longitude", source = "longitude", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    @Mapping(target = "openingHour", source = "openingHour", qualifiedByName = "toTimeString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "closingHour", source = "closingHour", qualifiedByName = "toTimeString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    ParkingLot toParkingLot(ParkingLotEntity parkingLotEntity);


    @Named("toParkingLotList")
    default List<ParkingLot> toParkingLotList(List<ParkingLotEntity> parkingLotEntityList) {
        return parkingLotEntityList.stream().map(this::toParkingLot).collect(Collectors.toList());
    }


    @Named("toParkingLotInformation")
    @Mapping(target = "parkingLot", source = "parkingLotEntity", qualifiedByName = "toParkingLot", defaultExpression = "java(customizedMapper.DEFAULT_PARKING_LOT)")
    @Mapping(target = "name", source = "name", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "address", source = "address", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "phone", source = "phone", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "ratingAverage", source = "ratingAverage", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    @Mapping(target = "numberOfRating", source = "NRating", defaultExpression = "java(customizedMapper.DEFAULT_SHORT_VALUE)")
    @Mapping(target = "availableSlot", source = "availableSlot", defaultExpression = "java(customizedMapper.DEFAULT_SHORT_VALUE)")
    @Mapping(target = "totalSlot", source = "totalSlot", defaultExpression = "java(customizedMapper.DEFAULT_SHORT_VALUE)")
    @Mapping(target = "imageData", source = "id", qualifiedByName = "toEncodedParkingLotImage", defaultExpression = "java(customizedMapper.DEFAULT_BYTE_STRING_VALUE)")
    ParkingLotInformation toParkingLotInformation(ParkingLotInformationEntity parkingLotInformationEntity) throws IOException;
}