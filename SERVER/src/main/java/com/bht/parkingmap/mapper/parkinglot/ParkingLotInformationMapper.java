package com.bht.parkingmap.mapper.parkinglot;

import java.io.IOException;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.entity.parkinglot.ParkingLotInformationEntity;
import com.bht.parkingmap.util.ImageUtil;

/**
 *
 * @author bht
 */
@Component
@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ParkingLotInformationMapper {

    default ParkingLotInformation toParkingLotInformation(ParkingLotInformationEntity parkingLotInformationEntity) throws IOException {
        return ParkingLotInformation.newBuilder()
                .setId(parkingLotInformationEntity.getId())
                .setName(parkingLotInformationEntity.getName())
                .setAddress(parkingLotInformationEntity.getAddress())
                .setPhone(parkingLotInformationEntity.getPhone())
                .setRatingAverage(parkingLotInformationEntity.getRatingAverage())
                .setNumberOfRating(parkingLotInformationEntity.getNRating())
                .setAvailableSlot(parkingLotInformationEntity.getAvailableSlot())
                .setTotalSlot(parkingLotInformationEntity.getTotalSlot())
                .setImageData(com.bht.parkingmap.android.util.ImageUtil.encodeImage(ImageUtil.getImage(
                        "plot/plot" + parkingLotInformationEntity.getId(), ImageUtil.ImageExtension.JPG)))
                .build();
    }
}