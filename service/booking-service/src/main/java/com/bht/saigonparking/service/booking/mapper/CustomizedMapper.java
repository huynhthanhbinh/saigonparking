package com.bht.saigonparking.service.booking.mapper;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.parkinglot.MapToParkingLotNameMapRequest;
import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc;
import com.bht.saigonparking.service.booking.configuration.AppConfiguration;
import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.fasterxml.uuid.Generators;
import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;

import lombok.Setter;

/**
 *
 * Mapper for the others & default object of each type
 *
 * Note that customized class and all of
 * its attributes, its methods should be declared as non-public
 * in order to hide this class and its methods, its attributes
 * from outside of mapper package
 *
 * @author bht
 */
@Component
@Setter(onMethod = @__(@Autowired))
@Mapper(componentModel = "spring", implementationPackage = AppConfiguration.BASE_PACKAGE + ".mapper.impl")
public abstract class CustomizedMapper {

    public static final String DEFAULT_STRING_VALUE = "";
    public static final Short DEFAULT_SHORT_VALUE = 0;
    public static final Integer DEFAULT_INT_VALUE = 0;
    public static final Long DEFAULT_LONG_VALUE = 0L;
    public static final Double DEFAULT_DOUBLE_VALUE = 0.0;
    public static final Boolean DEFAULT_BOOL_VALUE = Boolean.FALSE;
    public static final ByteString DEFAULT_BYTE_STRING_VALUE = ByteString.EMPTY;

    private ParkingLotServiceGrpc.ParkingLotServiceBlockingStub parkingLotServiceBlockingStub;

    @Named("generateUUID")
    public UUID generateUUID() {
        return Generators.timeBasedGenerator().generate();
    }

    @Named("toUUIDString")
    public String toUUIDString(@NotNull UUID uuid) {
        return uuid.toString();
    }

    @Named("toTimestampString")
    public String toTimestampString(@NotNull Timestamp timestamp) {
        return timestamp.toString();
    }

    @Named("toTimestamp")
    public Timestamp toTimestamp(@NotEmpty String timestampString) {
        return Timestamp.valueOf(timestampString);
    }

    @Named("toParkingLotName")
    public String toParkingLotName(@NotNull Long parkingLotId) {
        return parkingLotServiceBlockingStub.getParkingLotNameByParkingLotId(Int64Value.of(parkingLotId)).getValue();
    }

    @Named("toBookingEntityParkingLotNameMap")
    public Map<BookingEntity, String> toBookingEntityParkingLotNameMap(@NotNull List<BookingEntity> bookingEntityList) {
        if (!bookingEntityList.isEmpty()) {
            Map<Long, String> parkingLotIdNameMap = parkingLotServiceBlockingStub
                    .mapToParkingLotNameMap(MapToParkingLotNameMapRequest.newBuilder()
                            .addAllParkingLotId(bookingEntityList.stream()
                                    .map(BookingEntity::getParkingLotId)
                                    .collect(Collectors.toList()))
                            .build())
                    .getParkingLotNameMap();

            return bookingEntityList.stream().collect(Collectors
                    .toMap(bookingEntity -> bookingEntity,
                            bookingEntity -> parkingLotIdNameMap.get(bookingEntity.getParkingLotId())));
        }
        return Collections.emptyMap();
    }
}