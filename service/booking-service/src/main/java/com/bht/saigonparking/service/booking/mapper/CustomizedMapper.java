package com.bht.saigonparking.service.booking.mapper;

import java.sql.Time;
import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.service.booking.configuration.AppConfiguration;
import com.google.protobuf.ByteString;

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

    @Named("toTimeString")
    public String toTimeString(@NotNull Time time) {
        return time.toString();
    }

    @Named("toTime")
    public Time toTime(@NotEmpty String timeString) {
        return Time.valueOf(timeString);
    }

    @Named("toTimestampString")
    public String toTimestampString(@NotNull Timestamp timestamp) {
        return timestamp.toString();
    }

    @Named("toTimestamp")
    public Timestamp toTimestamp(@NotEmpty String timestampString) {
        return Timestamp.valueOf(timestampString);
    }
}