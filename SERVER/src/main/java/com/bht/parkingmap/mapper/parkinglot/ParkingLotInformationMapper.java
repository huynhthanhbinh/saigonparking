package com.bht.parkingmap.mapper.parkinglot;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

/**
 *
 * @author bht
 */
@Component
@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ParkingLotInformationMapper {
}