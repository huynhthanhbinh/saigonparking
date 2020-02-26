package com.bht.parkingmap.service;

import javax.validation.constraints.NotNull;

import com.bht.parkingmap.entity.report.ParkingLotReportEntity;
import com.bht.parkingmap.entity.report.ReportEntity;
import com.bht.parkingmap.entity.report.WrongParkingReportEntity;

/**
 *
 * @author bht
 */
public interface ReportService {

    ReportEntity getReportById(@NotNull Long id);

    ParkingLotReportEntity getParkingLotReportBydId(@NotNull Long id);

    WrongParkingReportEntity getWrongParkingReportEntity(@NotNull Long id);
}