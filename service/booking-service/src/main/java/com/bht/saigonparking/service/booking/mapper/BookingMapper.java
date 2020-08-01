package com.bht.saigonparking.service.booking.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.booking.Booking;
import com.bht.saigonparking.api.grpc.booking.BookingDetail;
import com.bht.saigonparking.api.grpc.booking.BookingHistory;
import com.bht.saigonparking.api.grpc.booking.CreateBookingRequest;
import com.bht.saigonparking.api.grpc.booking.UpdateBookingStatusRequest;
import com.bht.saigonparking.service.booking.configuration.AppConfiguration;
import com.bht.saigonparking.service.booking.entity.BookingEntity;
import com.bht.saigonparking.service.booking.entity.BookingHistoryEntity;

/**
 * implements all necessary mappers for BookingService
 *
 * @author bht
 */
@Component
@SuppressWarnings("UnmappedTargetProperties")
@Mapper(componentModel = "spring",
        implementationPackage = AppConfiguration.BASE_PACKAGE + ".mapper.impl",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class, UUID.class})
public interface BookingMapper {

    @Named("toBookingFromMapEntry")
    @Mapping(target = "id", source = "key.uuid", qualifiedByName = "toUUIDString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "parkingLotId", source = "key.parkingLotId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "parkingLotName", source = "value", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "customerId", source = "key.customerId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "licensePlate", source = "key.customerLicensePlate", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "createdAt", source = "key.createdAt", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "isFinished", source = "key.isFinished", defaultExpression = "java(customizedMapper.DEFAULT_BOOL_VALUE)")
    @Mapping(target = "latestStatus", source = "key.bookingStatusEntity", qualifiedByName = "toBookingStatus")
    Booking toBooking(@NotNull Map.Entry<BookingEntity, String> bookingEntityParkingLotNameEntry);

    @Named("toBooking")
    @Mapping(target = "id", source = "uuid", qualifiedByName = "toUUIDString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "parkingLotId", source = "parkingLotId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "parkingLotName", source = "parkingLotId", qualifiedByName = "toParkingLotName", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "customerId", source = "customerId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "licensePlate", source = "customerLicensePlate", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "isFinished", source = "isFinished", defaultExpression = "java(customizedMapper.DEFAULT_BOOL_VALUE)")
    @Mapping(target = "latestStatus", source = "bookingStatusEntity", qualifiedByName = "toBookingStatus")
    Booking toBooking(@NotNull BookingEntity bookingEntity);

    @Named("toBookingEntity")
    @Mapping(target = "parkingLotId", source = "parkingLotId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "customerId", source = "customerId", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "customerLicensePlate", source = "licensePlate", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "bookingStatusEntity", expression = "java(enumMapper.getDefaultBookingStatusEntity())")
    @Mapping(target = "isFinished", constant = "false")
    @Mapping(target = "version", constant = "1L")
    @Mapping(target = "uuid", expression = "java(customizedMapper.generateUUID())")
    @Mapping(target = "id", ignore = true)
    BookingEntity toBookingEntity(@NotNull CreateBookingRequest bookingRequest);

    @Named("toBookingHistory")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "status", source = "bookingStatusEntity", qualifiedByName = "toBookingStatus")
    @Mapping(target = "timestamp", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "note", source = "note", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    BookingHistory toBookingHistory(@NotNull BookingHistoryEntity bookingHistoryEntity);

    @Named("toBookingHistoryEntity")
    @Mapping(target = "bookingEntity", ignore = true)
    @Mapping(target = "bookingStatusEntity", source = "status", qualifiedByName = "toBookingStatusEntity")
    @Mapping(target = "note", source = "note", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "lastUpdated", ignore = true)
    @Mapping(target = "version", constant = "1L")
    @Mapping(target = "id", ignore = true)
    BookingHistoryEntity toBookingHistoryEntity(@NotNull UpdateBookingStatusRequest updateBookingStatusRequest);

    @Named("toBookingDetail")
    default BookingDetail toBookingDetail(@NotNull BookingEntity bookingEntity) {
        return BookingDetail.newBuilder()
                .setBooking(toBooking(bookingEntity))
                .addAllHistory(toBookingHistoryList(bookingEntity.getBookingHistoryEntitySet()))
                .build();
    }

    @Named("toBookingList")
    default List<Booking> toBookingList(@NotNull Map<BookingEntity, String> bookingEntityParkingLotNameMap) {
        return bookingEntityParkingLotNameMap.entrySet().stream()
                .sorted(BookingEntity.SORT_BY_CREATED_AT_THEN_BY_BOOKING_ID)
                .map(this::toBooking)
                .collect(Collectors.toList());
    }

    @Named("toBookingHistoryList")
    default List<BookingHistory> toBookingHistoryList(@NotNull Set<BookingHistoryEntity> bookingHistoryEntitySet) {
        return bookingHistoryEntitySet.stream()
                .sorted(BookingHistoryEntity.SORT_BY_LAST_UPDATED_THEN_BY_ID)
                .map(this::toBookingHistory)
                .collect(Collectors.toList());
    }
}