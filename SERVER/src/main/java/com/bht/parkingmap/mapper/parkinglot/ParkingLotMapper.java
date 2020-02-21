package com.bht.parkingmap.mapper.parkinglot;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.parkinglot.ParkingLot;

/**
 *
 * @author bht
 */
@Component
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ParkingLotMapper {

    static ParkingLot toParkingLot(Tuple parkingLotDistanceTuple) {
        return ParkingLot.newBuilder()
                .setId(parkingLotDistanceTuple.get(0, BigInteger.class).longValue())
                .setName(parkingLotDistanceTuple.get(1, String.class))
                .setType(parkingLotDistanceTuple.get(2, Byte.class))
                .setLatitude(parkingLotDistanceTuple.get(3, Double.class))
                .setLongitude(parkingLotDistanceTuple.get(4, Double.class))
                .setOpeningHour(parkingLotDistanceTuple.get(5, Time.class).toString())
                .setClosingHour(parkingLotDistanceTuple.get(6, Time.class).toString())
                .setLastUpdated(parkingLotDistanceTuple.get(7, Timestamp.class).toString())
                .setDistance(parkingLotDistanceTuple.get(8, Double.class))
                .build();
    }

    default List<ParkingLot> toParkingLotList(List<Tuple> parkingLotDistanceTupleList) {
        return parkingLotDistanceTupleList.stream().map(ParkingLotMapper::toParkingLot).collect(Collectors.toList());
    }
}