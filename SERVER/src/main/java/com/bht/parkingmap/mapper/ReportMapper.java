package com.bht.parkingmap.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.report.ParkingLotReport;
import com.bht.parkingmap.api.proto.report.Report;
import com.bht.parkingmap.api.proto.report.WrongParkingReport;
import com.bht.parkingmap.entity.report.ParkingLotReportEntity;
import com.bht.parkingmap.entity.report.ReportEntity;
import com.bht.parkingmap.entity.report.WrongParkingReportEntity;

/**
 *
 * @author bht
 */
@Component
@SuppressWarnings("UnmappedTargetProperties")
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {EnumMapper.class, CustomizedMapper.class, UserMapper.class})
public interface ReportMapper {

    @Named("toReport")
    @Mapping(target = "id", source = "id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "type", source = "reportTypeEntity.id", qualifiedByName = "toReportType", defaultExpression = "java(customizedMapper.DEFAULT_REPORT_TYPE)")
    @Mapping(target = "customer", source = "customerEntity", qualifiedByName = "toCustomerWithoutUserInfo", defaultExpression = "java(customizedMapper.DEFAULT_CUSTOMER)")
    @Mapping(target = "isHandled", source = "isHandled", defaultExpression = "java(customizedMapper.DEFAULT_BOOL_VALUE)")
    @Mapping(target = "imageData", source = "id", qualifiedByName = "toEncodedReportImage", defaultExpression = "java(customizedMapper.DEFAULT_BYTE_STRING_VALUE)")
    @Mapping(target = "lastUpdated", source = "lastUpdated", qualifiedByName = "toTimestampString", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    Report toReport(ReportEntity reportEntity);


    @Named("toParkingLotReport")
    @Mapping(target = "reportInfo", source = "parkingLotReportEntity", qualifiedByName = "toReport", defaultExpression = "java(customizedMapper.DEFAULT_REPORT)")
    @Mapping(target = "parkingLotId", source = "parkingLotEntity.id", defaultExpression = "java(customizedMapper.DEFAULT_LONG_VALUE)")
    @Mapping(target = "content", source = "violation", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    ParkingLotReport toParkingLotReport(ParkingLotReportEntity parkingLotReportEntity);


    @Named("toWrongParkingReport")
    @Mapping(target = "reportInfo", source = "wrongParkingReportEntity", qualifiedByName = "toReport", defaultExpression = "java(customizedMapper.DEFAULT_REPORT)")
    @Mapping(target = "registrationPlate", source = "registrationPlate", defaultExpression = "java(customizedMapper.DEFAULT_STRING_VALUE)")
    @Mapping(target = "latitude", source = "latitude", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    @Mapping(target = "longitude", source = "longitude", defaultExpression = "java(customizedMapper.DEFAULT_DOUBLE_VALUE)")
    WrongParkingReport toWrongParkingReport(WrongParkingReportEntity wrongParkingReportEntity);
}