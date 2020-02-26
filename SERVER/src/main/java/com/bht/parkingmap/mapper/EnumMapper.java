package com.bht.parkingmap.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotType;
import com.bht.parkingmap.api.proto.report.ReportType;
import com.bht.parkingmap.api.proto.user.UserRole;
import com.bht.parkingmap.base.BaseBean;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 *
 * @author bht
 */
@Component
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class EnumMapper implements BaseBean {

    private static final BiMap<Integer, UserRole> USER_ROLE_MAP = HashBiMap.create(5);
    private static final BiMap<Integer, ParkingLotType> PARKING_LOT_TYPE_MAP = HashBiMap.create(4);
    private static final BiMap<Integer, ReportType> REPORT_TYPE_MAP = HashBiMap.create(3);

    @Override
    public void initialize() {
        initUserRoleMap();
        initParkingLotTypeMap();
        initReportTypeMap();
    }

    @Named("toUserRole")
    UserRole toUserRole(Integer userRoleId) {
        return USER_ROLE_MAP.get(userRoleId);
    }

    @Named("toUserRoleId")
    public Integer toUserRoleId(UserRole userRole) {
        return USER_ROLE_MAP.inverse().get(userRole);
    }

    @Named("toParkingLotType")
    ParkingLotType toParkingLotType(Integer parkingLotTypeId) {
        return PARKING_LOT_TYPE_MAP.get(parkingLotTypeId);
    }

    @Named("toParkingLotId")
    public Integer toParkingLotTypeId(ParkingLotType parkingLotType) {
        return PARKING_LOT_TYPE_MAP.inverse().get(parkingLotType);
    }

    @Named("toReportType")
    ReportType toReportType(Integer reportTypeId) {
        return REPORT_TYPE_MAP.get(reportTypeId);
    }

    @Named("toReportTypeId")
    public Integer toReportTypeId(ReportType reportType) {
        return REPORT_TYPE_MAP.inverse().get(reportType);
    }

    private void initUserRoleMap() {
        USER_ROLE_MAP.put(0, UserRole.UNRECOGNIZED);
        USER_ROLE_MAP.put(1, UserRole.ADMIN);
        USER_ROLE_MAP.put(2, UserRole.CUSTOMER);
        USER_ROLE_MAP.put(3, UserRole.PARKING_LOT_EMPLOYEE);
        USER_ROLE_MAP.put(4, UserRole.GOVERNMENT_EMPLOYEE);
    }

    private void initParkingLotTypeMap() {
        PARKING_LOT_TYPE_MAP.put(0, ParkingLotType.UNRECOGNIZED);
        PARKING_LOT_TYPE_MAP.put(1, ParkingLotType.PRIVATE);
        PARKING_LOT_TYPE_MAP.put(2, ParkingLotType.BUILDING);
        PARKING_LOT_TYPE_MAP.put(3, ParkingLotType.STREET);
    }

    private void initReportTypeMap() {
        REPORT_TYPE_MAP.put(0, ReportType.UNRECOGNIZED);
        REPORT_TYPE_MAP.put(1, ReportType.PARKING_LOT_REPORT);
        REPORT_TYPE_MAP.put(2, ReportType.WRONG_PARKING_REPORT);
    }
}