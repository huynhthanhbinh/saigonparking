package com.bht.parkingmap.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;

import com.bht.parkingmap.api.parkinglot.ParkingLotType;
import com.bht.parkingmap.api.report.ReportType;
import com.bht.parkingmap.api.user.UserRole;
import com.bht.parkingmap.base.BaseBean;

import javassist.NotFoundException;

/**
 *
 * @author bht
 */
@Component
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class EnumMapper implements BaseBean {

    private Map<Integer, UserRole> userRoleMap;
    private Map<Integer, ParkingLotType> parkingLotTypeMap;
    private Map<Integer, ReportType> reportTypeMap;

    @Override
    public void initialize() {
        initUserRoleMap();
        initParkingLotTypeMap();
        initReportTypeMap();
    }

    @Named("toUserRole")
    UserRole toUserRole(Integer userRoleId) {
        return userRoleMap.get(userRoleId);
    }

    @Named("toUserRoleId")
    Integer toUserRoleId(UserRole userRole) throws NotFoundException {
        return getFirstKeyOfValue(userRoleMap, userRole);
    }

    @Named("toParkingLotType")
    ParkingLotType toParkingLotType(Integer parkingLotTypeId) {
        return parkingLotTypeMap.get(parkingLotTypeId);
    }

    @Named("toParkingLotId")
    Integer toParkingLotTypeId(ParkingLotType parkingLotType) throws NotFoundException {
        return getFirstKeyOfValue(parkingLotTypeMap, parkingLotType);
    }

    @Named("toReportType")
    ReportType toReportType(Integer reportTypeId) {
        return reportTypeMap.get(reportTypeId);
    }

    @Named("toReportTypeId")
    Integer toReportTypeId(ReportType reportType) throws NotFoundException {
        return getFirstKeyOfValue(reportTypeMap, reportType);
    }

    private <T, E> T getFirstKeyOfValue(Map<T, E> entrySet, E value) throws NotFoundException {
        return entrySet.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Not found compatible key !!!"));
    }

    private void initUserRoleMap() {
        userRoleMap = new HashMap<>();
        userRoleMap.put(1, UserRole.ADMIN);
        userRoleMap.put(2, UserRole.CUSTOMER);
        userRoleMap.put(3, UserRole.PARKING_LOT_EMPLOYEE);
        userRoleMap.put(4, UserRole.GOVERNMENT_EMPLOYEE);
    }

    private void initParkingLotTypeMap() {
        parkingLotTypeMap = new HashMap<>();
        parkingLotTypeMap.put(1, ParkingLotType.PRIVATE);
        parkingLotTypeMap.put(2, ParkingLotType.BUILDING);
        parkingLotTypeMap.put(3, ParkingLotType.STREET);
    }

    private void initReportTypeMap() {
        reportTypeMap = new HashMap<>();
        reportTypeMap.put(1, ReportType.PARKING_LOT_REPORT);
        reportTypeMap.put(2, ReportType.WRONG_PARKING_REPORT);
    }
}