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

import com.bht.parkingmap.api.parkinglot.ParkingLot;
import com.bht.parkingmap.api.parkinglot.ParkingLotInformation;
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
    @Mapping(target = "openingHour", expression = "java(customizedMapper.toTimeString(parkingLotDistanceTuple.get(5, java.sql.Time.class)))")
    @Mapping(target = "closingHour", expression = "java(customizedMapper.toTimeString(parkingLotDistanceTuple.get(6, java.sql.Time.class)))")
    @Mapping(target = "lastUpdated", expression = "java(customizedMapper.toTimestampString(parkingLotDistanceTuple.get(7, java.sql.Timestamp.class)))")
    @Mapping(target = "distance", expression = "java(parkingLotDistanceTuple.get(8, Double.class))")
    ParkingLot toParkingLotWithDistance(Tuple parkingLotDistanceTuple);


    @Named("toParkingLotListWithDistance")
    default List<ParkingLot> toParkingLotListWithDistance(List<Tuple> parkingLotDistanceTupleList) {
        return parkingLotDistanceTupleList.stream().map(this::toParkingLotWithDistance).collect(Collectors.toList());
    }


    @Named("toParkingLot")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "parkingLotTypeEntity.id", qualifiedByName = "toParkingLotType")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "openingHour", source = "openingHour", qualifiedByName = "toTimeString")
    @Mapping(target = "closingHour", source = "closingHour", qualifiedByName = "toTimeString")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString")
    ParkingLot toParkingLot(ParkingLotEntity parkingLotEntity);


    @Named("toParkingLotList")
    default List<ParkingLot> toParkingLotList(List<ParkingLotEntity> parkingLotEntityList) {
        return parkingLotEntityList.stream().map(this::toParkingLot).collect(Collectors.toList());
    }


    @Named("toParkingLotInformation")
    @Mapping(target = "parkingLot", source = "parkingLotEntity", qualifiedByName = "toParkingLot")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "ratingAverage", source = "ratingAverage")
    @Mapping(target = "numberOfRating", source = "NRating")
    @Mapping(target = "availableSlot", source = "availableSlot")
    @Mapping(target = "totalSlot", source = "totalSlot")
    @Mapping(target = "imageData", source = "id", qualifiedByName = "toEncodedParkingLotImage")
    ParkingLotInformation toParkingLotInformation(ParkingLotInformationEntity parkingLotInformationEntity) throws IOException;
}