package com.bht.saigonparking.service.booking.mapper;

import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.booking.Booking;
import com.bht.saigonparking.api.grpc.booking.BookingHistory;
import com.bht.saigonparking.api.grpc.booking.CreateBookingRequest;
import com.bht.saigonparking.service.booking.configuration.AppConfiguration;
import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;

/**
 *
 * @author bht
 */
@Component
@SuppressWarnings("UnmappedTargetProperties")
@Mapper(componentModel = "spring",
        implementationPackage = AppConfiguration.BASE_PACKAGE + ".mapper.impl",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class})
public interface BookingMapper {

    @Named("toBooking")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "parkingLotId", source = "parkingLotId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "customerId", source = "customerId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "licensePlate", source = "customerLicensePlate", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "isFinished", source = "isFinished", defaultExpression = "java(customizedMapper.DEFAULT_BOOL_VALUE)")
    Booking toBooking(@NotNull BookingEntity bookingEntity);

    @Named("toBookingEntity")
    @Mapping(target = "parkingLotId", source = "parkingLotId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "customerId", source = "customerId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "customerLicensePlate", source = "licensePlate", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isFinished", ignore = true)
    @Mapping(target = "version", ignore = true)
    BookingEntity toBookingEntity(@NotNull CreateBookingRequest bookingRequest);

    @Named("toBookingHistory")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "bookingId", source = "bookingEntity.id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "status", source = "bookingStatusEntity", qualifiedByName = "toBookingStatus")
    @Mapping(target = "timestamp", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "note", source = "note", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    BookingHistory toBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity);
}