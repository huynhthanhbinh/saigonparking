package com.bht.parkingmap.dbserver.service.grpc;

import java.util.List;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLot;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotList;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotScanningByRadius;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceImplBase;
import com.bht.parkingmap.dbserver.mapper.ParkingLotMapper;
import com.bht.parkingmap.dbserver.service.ParkingLotService;
import com.bht.parkingmap.dbserver.util.LoggingUtil;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author bht
 */
@GRpcService
public final class ParkingLotServiceGrpcImpl extends ParkingLotServiceImplBase {

    private final ParkingLotService parkingLotService;
    private final ParkingLotMapper parkingLotMapper;


    @Autowired
    public ParkingLotServiceGrpcImpl(ParkingLotService parkingLotService,
                                     ParkingLotMapper parkingLotMapper) {

        this.parkingLotService = parkingLotService;
        this.parkingLotMapper = parkingLotMapper;
    }

    @Override
    public void getTopParkingLotInRegionOrderByDistanceWithName(ParkingLotScanningByRadius request, StreamObserver<ParkingLotList> responseObserver) {
        try {
            List<ParkingLot> parkingLotList = parkingLotMapper.toParkingLotWithNameList(
                    parkingLotService.getTopParkingLotInRegionOrderByDistanceWithName(
                            request.getLatitude(),
                            request.getLongitude(),
                            request.getRadiusToScan(),
                            request.getNResult()));

            responseObserver.onNext(ParkingLotList.newBuilder().addAllParkingLot(parkingLotList).build());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getTopParkingLotInRegionOrderByDistanceWithName(%f, %f, %d, %d)",
                            request.getLatitude(), request.getLongitude(), request.getRadiusToScan(), request.getNResult()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getTopParkingLotInRegionOrderByDistanceWithName(%f, %f, %d, %d)",
                            request.getLatitude(), request.getLongitude(), request.getRadiusToScan(), request.getNResult()));
        }
    }

    @Override
    public void getTopParkingLotInRegionOrderByDistanceWithoutName(ParkingLotScanningByRadius request, StreamObserver<ParkingLotList> responseObserver) {
        try {
            List<ParkingLot> parkingLotList = parkingLotMapper.toParkingLotWithoutNameList(
                    parkingLotService.getTopParkingLotInRegionOrderByDistanceWithoutName(
                            request.getLatitude(),
                            request.getLongitude(),
                            request.getRadiusToScan(),
                            request.getNResult()));

            responseObserver.onNext(ParkingLotList.newBuilder().addAllParkingLot(parkingLotList).build());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getTopParkingLotInRegionOrderByDistanceWithoutName(%f, %f, %d, %d)",
                            request.getLatitude(), request.getLongitude(), request.getRadiusToScan(), request.getNResult()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getTopParkingLotInRegionOrderByDistanceWithoutName(%f, %f, %d, %d)",
                            request.getLatitude(), request.getLongitude(), request.getRadiusToScan(), request.getNResult()));
        }
    }

    @Override
    public void getParkingLotInformationByParkingLotId(Int64Value request, StreamObserver<ParkingLotInformation> responseObserver) {
        try {
            ParkingLotInformation parkingLotInformation = parkingLotMapper.toParkingLotInformation(
                    parkingLotService.getParkingLotInformationByParkingLotId(request.getValue()));

            responseObserver.onNext(parkingLotInformation);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getParkingLotInformationByParkingLotId(%d)", request.getValue()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotInformationByParkingLotId(%d)", request.getValue()));
        }
    }
}