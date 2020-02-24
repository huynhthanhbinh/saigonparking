package com.bht.parkingmap.mapper;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.util.ImageUtil;
import com.google.protobuf.ByteString;

/**
 *
 * @author bht
 */
@Component
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CustomizedMapper {

    @Named("toTimeString")
    default String toTimeString(Time time) {
        return time.toString();
    }

    @Named("toTimestampString")
    default String toTimestampString(Timestamp timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(timestamp);
    }

    @Named("toEncodedParkingLotImage")
    default ByteString toEncodedParkingLotImage(Integer parkingLotId) throws IOException {
        return com.bht.parkingmap.android.util.ImageUtil.encodeImage(ImageUtil.getImage(
                "plot/plot" + parkingLotId, ImageUtil.ImageExtension.JPG));
    }
}