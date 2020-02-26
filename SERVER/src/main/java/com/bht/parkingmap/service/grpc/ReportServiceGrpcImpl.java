package com.bht.parkingmap.service.grpc;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.parkingmap.api.proto.report.ParkingLotReport;
import com.bht.parkingmap.api.proto.report.Report;
import com.bht.parkingmap.api.proto.report.ReportServiceGrpc;
import com.bht.parkingmap.api.proto.report.WrongParkingReport;
import com.bht.parkingmap.mapper.ReportMapper;
import com.bht.parkingmap.service.ReportService;
import com.bht.parkingmap.util.LoggingUtil;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author bht
 */
@GRpcService
public class ReportServiceGrpcImpl extends ReportServiceGrpc.ReportServiceImplBase {

    private final ReportService reportService;
    private final ReportMapper reportMapper;

    @Autowired
    public ReportServiceGrpcImpl(ReportService reportService, ReportMapper reportMapper) {
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @Override
    public void getReportById(Int64Value request, StreamObserver<Report> responseObserver) {
        try {
            Report report = reportMapper.toReport(reportService
                    .getReportById(request.getValue()));

            responseObserver.onNext(report);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getReportById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getReportById(%d)", request.getValue()));
        }
    }

    @Override
    public void getParkingLotReportById(Int64Value request, StreamObserver<ParkingLotReport> responseObserver) {
        try {
            ParkingLotReport parkingLotReport = reportMapper.toParkingLotReport(reportService
                    .getParkingLotReportBydId(request.getValue()));

            responseObserver.onNext(parkingLotReport);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getParkingLotReportById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotReportById(%d)", request.getValue()));
        }
    }

    @Override
    public void getWrongParkingReportById(Int64Value request, StreamObserver<WrongParkingReport> responseObserver) {
        try {
            WrongParkingReport wrongParkingReport = reportMapper.toWrongParkingReport(reportService
                    .getWrongParkingReportEntity(request.getValue()));

            responseObserver.onNext(wrongParkingReport);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getWrongParkingReportById(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getWrongParkingReportById(%d)", request.getValue()));
        }
    }
}