package com.bht.parkingmap.dbserver.mapper;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotType;
import com.bht.parkingmap.api.proto.user.Customer;
import com.bht.parkingmap.api.proto.user.User;
import com.bht.parkingmap.api.proto.user.UserRole;
import com.bht.parkingmap.dbserver.util.ImageUtil;
import com.google.protobuf.ByteString;

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
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
abstract class CustomizedMapper {

    static final String DEFAULT_STRING_VALUE = "";
    static final Short DEFAULT_SHORT_VALUE = 0;
    static final Integer DEFAULT_INT_VALUE = 0;
    static final Long DEFAULT_LONG_VALUE = 0L;
    static final Double DEFAULT_DOUBLE_VALUE = 0.0;
    static final Boolean DEFAULT_BOOL_VALUE = Boolean.FALSE;
    static final ByteString DEFAULT_BYTE_STRING_VALUE = ByteString.EMPTY;

    static final UserRole DEFAULT_USER_ROLE = UserRole.UNRECOGNIZED;
    static final ParkingLotType DEFAULT_PARKING_LOT_TYPE = ParkingLotType.UNRECOGNIZED;

    static final User DEFAULT_USER = User.getDefaultInstance();
    static final Customer DEFAULT_CUSTOMER = Customer.getDefaultInstance();
    static final ParkingLotInformation DEFAULT_PARKING_LOT_INFORMATION = ParkingLotInformation.getDefaultInstance();


    @Named("toTimeString")
    String toTimeString(@NotNull Time time) {
        return time.toString();
    }

    @Named("toTime")
    Time toTime(@NotNull String timeString) {
        return Time.valueOf(timeString);
    }

    @Named("toTimestampString")
    String toTimestampString(@NotNull Timestamp timestamp) {
        return timestamp.toString();
    }

    @Named("toTimestamp")
    Timestamp toTimestamp(@NotNull String timestampString) {
        return Timestamp.valueOf(timestampString);
    }

    @Named("toEncodedParkingLotImage")
    ByteString toEncodedParkingLotImage(@NotNull Integer parkingLotId) throws IOException {
        return com.bht.parkingmap.api.util.ImageUtil.encodeImage(ImageUtil.getImage(
                "plot/plot" + parkingLotId, ImageUtil.ImageExtension.JPG));
    }
}