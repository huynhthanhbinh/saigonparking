package com.bht.saigonparking.service.booking.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.booking.BookingStatus;
import com.bht.saigonparking.common.base.BaseBean;
import com.bht.saigonparking.service.booking.configuration.AppConfiguration;
import com.bht.saigonparking.service.booking.entity.BookingStatusEntity;
import com.bht.saigonparking.service.booking.repository.core.BookingStatusRepository;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import lombok.Setter;

/**
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

    private static final BiMap<BookingStatusEntity, BookingStatus> BOOKING_STATUS_BI_MAP = HashBiMap.create();
    private static final Map<Long, Long> BOOKING_STATUS_VALUE_MAP = new HashMap<>();
    private BookingStatusRepository bookingStatusRepository;

    @Override
    public void initialize() {
        initBookingStatusBiMap();
        initBookingStatusValueMap();
    }

    @Named("toBookingStatus")
    public BookingStatus toBookingStatus(@NotNull BookingStatusEntity bookingStatusEntity) {
        return BOOKING_STATUS_BI_MAP.get(bookingStatusEntity);
    }

    @Named("toBookingStatusEntity")
    public BookingStatusEntity toBookingStatusEntity(@NotNull BookingStatus bookingStatus) {
        return BOOKING_STATUS_BI_MAP.inverse().get(bookingStatus);
    }

    @Named("toBookingStatusValue")
    public Long toBookingStatusValue(Long bookingStatusId) {
        return BOOKING_STATUS_VALUE_MAP.get(bookingStatusId);
    }

    public BookingStatusEntity getDefaultBookingStatusEntity() {
        return toBookingStatusEntity(BookingStatus.CREATED);
    }

    private void initBookingStatusBiMap() {
        BOOKING_STATUS_BI_MAP.put(getBookingStatusByStatus("CREATED"), BookingStatus.CREATED);
        BOOKING_STATUS_BI_MAP.put(getBookingStatusByStatus("ACCEPTED"), BookingStatus.ACCEPTED);
        BOOKING_STATUS_BI_MAP.put(getBookingStatusByStatus("REJECTED"), BookingStatus.REJECTED);
        BOOKING_STATUS_BI_MAP.put(getBookingStatusByStatus("CANCELLED"), BookingStatus.CANCELLED);
        BOOKING_STATUS_BI_MAP.put(getBookingStatusByStatus("FINISHED"), BookingStatus.FINISHED);
    }

    private void initBookingStatusValueMap() {
        BOOKING_STATUS_VALUE_MAP.putAll(BOOKING_STATUS_BI_MAP.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getId(), entry -> (long) entry.getValue().getNumber())));
    }

    private BookingStatusEntity getBookingStatusByStatus(@NotEmpty String status) {
        return bookingStatusRepository.findByStatus(status).orElseThrow(EntityNotFoundException::new);
    }
}