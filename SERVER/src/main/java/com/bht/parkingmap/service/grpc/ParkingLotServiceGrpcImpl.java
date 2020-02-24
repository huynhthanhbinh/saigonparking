package com.bht.parkingmap.service.grpc;

import java.util.List;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.parkingmap.api.parkinglot.ParkingLot;
import com.bht.parkingmap.api.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.parkinglot.ParkingLotList;
import com.bht.parkingmap.api.parkinglot.ParkingLotScanningByRadius;
import com.bht.parkingmap.api.parkinglot.ParkingLotScanningInRegion;
import com.bht.parkingmap.api.parkinglot.ParkingLotServiceGrpc;
import com.bht.parkingmap.mapper.ParkingLotMapper;
import com.bht.parkingmap.service.ParkingLotService;
import com.bht.parkingmap.util.LoggingUtil;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author bht
 */
@Log4j
@GRpcService
public final class ParkingLotServiceGrpcImpl extends ParkingLotServiceGrpc.ParkingLotServiceImplBase {

    private final ParkingLotMapper parkingLotMapper;
    private final ParkingLotService parkingLotService;


    @Autowired
    public ParkingLotServiceGrpcImpl(ParkingLotService parkingLotService,
                                     ParkingLotMapper parkingLotMapper) {

        this.parkingLotService = parkingLotService;
        this.parkingLotMapper = parkingLotMapper;
    }


    @Override
    public void getAllParkingLotCurrentlyWorkingByRadius(ParkingLotScanningByRadius request, StreamObserver<ParkingLotList> responseObserver) {
        try {
            List<ParkingLot> parkingLotList = parkingLotMapper.toParkingLotListWithDistance(
                    parkingLotService.getAllParkingLotCurrentlyWorkingByRadius(
                            request.getLatitude(),
                            request.getLongitude(),
                            request.getRadiusToScan()));

            responseObserver.onNext(ParkingLotList.newBuilder().addAllParkingLot(parkingLotList).build());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllParkingLotCurrentlyWorkingByRadius(%f, %f, %d)",
                            request.getLatitude(), request.getLongitude(), request.getRadiusToScan()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllParkingLotCurrentlyWorkingByRadius(%f, %f, %d)",
                            request.getLatitude(), request.getLongitude(), request.getRadiusToScan()));
        }
    }


    @Override
    public void getAllParkingLotCurrentlyWorkingInRegion(ParkingLotScanningInRegion request, StreamObserver<ParkingLotList> responseObserver) {
        try {
            List<ParkingLot> parkingLotList = parkingLotMapper.toParkingLotList(
                    parkingLotService.getAllParkingLotCurrentlyWorkingInRegion(
                            request.getNorthEastLat(),
                            request.getNorthEastLng(),
                            request.getSouthWestLat(),
                            request.getSouthWestLng()));

            responseObserver.onNext(ParkingLotList.newBuilder().addAllParkingLot(parkingLotList).build());
            responseObserver.onCompleted();

            LoggingUtil.log(Level.INFO, "SERVICE", "Success",
                    String.format("getAllParkingLotCurrentlyWorkingInRegion(%f, %f, %f, %f)",
                            request.getNorthEastLat(), request.getNorthEastLng(), request.getSouthWestLat(), request.getSouthWestLng()));

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllParkingLotCurrentlyWorkingInRegion(%f, %f, %f, %f)",
                            request.getNorthEastLat(), request.getNorthEastLng(), request.getSouthWestLat(), request.getSouthWestLng()));
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