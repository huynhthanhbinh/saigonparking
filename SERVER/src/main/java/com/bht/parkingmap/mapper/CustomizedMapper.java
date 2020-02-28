package com.bht.parkingmap.mapper;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLot;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotType;
import com.bht.parkingmap.api.proto.user.Customer;
import com.bht.parkingmap.api.proto.user.User;
import com.bht.parkingmap.api.proto.user.UserRole;
import com.bht.parkingmap.util.ImageUtil;
import com.google.protobuf.ByteString;

/**
 *
 * @author bht
 */
@Component
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class CustomizedMapper {

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
    static final ParkingLot DEFAULT_PARKING_LOT = ParkingLot.getDefaultInstance();


    @Named("toTimeString")
    String toTimeString(Time time) {
        return time.toString();
    }

    @Named("toTimestampString")
    String toTimestampString(Timestamp timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(timestamp);
    }

    @Named("toEncodedParkingLotImage")
    ByteString toEncodedParkingLotImage(Integer parkingLotId) throws IOException {
        return com.bht.parkingmap.api.util.ImageUtil.encodeImage(ImageUtil.getImage(
                "plot/plot" + parkingLotId, ImageUtil.ImageExtension.JPG));
    }
}