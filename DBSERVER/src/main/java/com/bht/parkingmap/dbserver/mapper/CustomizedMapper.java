package com.bht.parkingmap.dbserver.mapper;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotType;
import com.bht.parkingmap.api.proto.user.Customer;
import com.bht.parkingmap.api.proto.user.User;
import com.bht.parkingmap.api.proto.user.UserRole;
import com.bht.parkingmap.dbserver.configuration.AppConfiguration;
import com.bht.parkingmap.dbserver.service.extra.ImageService;
import com.google.protobuf.ByteString;

import lombok.Setter;

/**
 *
 * Mapper for the others & default object of each type
 *
 * Note that customized class and all of
 * its attributes, its methods are declared as non-public
 * in order to hide this class and its methods, its attributes
 * from outside of mapper package
 *
 * It is expected to be seen only by mapper class
 *
 * @author bht
 */
@Component
@Setter(onMethod = @__(@Autowired))
@Mapper(componentModel = "spring",
        implementationPackage = AppConfiguration.BASE_PACKAGE_SERVER + ".mapper.impl",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class CustomizedMapper {

    private ImageService imageService;

    public static final String DEFAULT_STRING_VALUE = "";
    public static final Short DEFAULT_SHORT_VALUE = 0;
    public static final Integer DEFAULT_INT_VALUE = 0;
    public static final Long DEFAULT_LONG_VALUE = 0L;
    public static final Double DEFAULT_DOUBLE_VALUE = 0.0;
    public static final Boolean DEFAULT_BOOL_VALUE = Boolean.FALSE;
    public static final ByteString DEFAULT_BYTE_STRING_VALUE = ByteString.EMPTY;

    public static final UserRole DEFAULT_USER_ROLE = UserRole.UNRECOGNIZED;
    public static final ParkingLotType DEFAULT_PARKING_LOT_TYPE = ParkingLotType.UNRECOGNIZED;

    public static final User DEFAULT_USER = User.getDefaultInstance();
    public static final Customer DEFAULT_CUSTOMER = Customer.getDefaultInstance();
    public static final ParkingLotInformation DEFAULT_PARKING_LOT_INFORMATION = ParkingLotInformation.getDefaultInstance();

    @Named("toTimeString")
    public String toTimeString(@NotNull Time time) {
        return time.toString();
    }

    @Named("toTime")
    public Time toTime(@NotNull String timeString) {
        return Time.valueOf(timeString);
    }

    @Named("toTimestampString")
    public String toTimestampString(@NotNull Timestamp timestamp) {
        return timestamp.toString();
    }

    @Named("toTimestamp")
    public Timestamp toTimestamp(@NotNull String timestampString) {
        return Timestamp.valueOf(timestampString);
    }

    @Named("toEncodedParkingLotImage")
    public ByteString toEncodedParkingLotImage(@NotNull Integer parkingLotId) throws IOException {
        return com.bht.parkingmap.api.util.ImageUtil.encodeImage(imageService.getImage(
                "plot" + parkingLotId, ImageService.ImageExtension.JPG));
    }
}