package com.bht.parkingmap.service.impl;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.entity.report.ParkingLotReportEntity;
import com.bht.parkingmap.entity.report.ReportEntity;
import com.bht.parkingmap.entity.report.WrongParkingReportEntity;
import com.bht.parkingmap.repository.report.ParkingLotReportRepository;
import com.bht.parkingmap.repository.report.ReportRepository;
import com.bht.parkingmap.repository.report.WrongParkingReportRepository;
import com.bht.parkingmap.service.ReportService;

/**
 *
 * @author bht
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ParkingLotReportRepository parkingLotReportRepository;
    private final WrongParkingReportRepository wrongParkingReportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             ParkingLotReportRepository parkingLotReportRepository,
                             WrongParkingReportRepository wrongParkingReportRepository) {

        this.reportRepository = reportRepository;
        this.parkingLotReportRepository = parkingLotReportRepository;
        this.wrongParkingReportRepository = wrongParkingReportRepository;
    }

    @Override
    public ReportEntity getReportById(@NotNull Long id) {
        return reportRepository.getOne(id);
    }

    @Override
    public ParkingLotReportEntity getParkingLotReportBydId(@NotNull Long id) {
        return parkingLotReportRepository.getOne(id);
    }

    @Override
    public WrongParkingReportEntity getWrongParkingReportEntity(@NotNull Long id) {
        return wrongParkingReportRepository.getOne(id);
    }
}