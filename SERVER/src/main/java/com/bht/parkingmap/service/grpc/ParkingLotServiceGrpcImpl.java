package com.bht.parkingmap.service.grpc;

import java.util.List;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.parkingmap.api.parkinglot.ParkingLot;
import com.bht.parkingmap.api.parkinglot.ParkingLotList;
import com.bht.parkingmap.api.parkinglot.ParkingLotScanningByRadius;
import com.bht.parkingmap.api.parkinglot.ParkingLotScanningInRegion;
import com.bht.parkingmap.api.parkinglot.ParkingLotServiceGrpc;
import com.bht.parkingmap.mapper.parkinglot.ParkingLotMapper;
import com.bht.parkingmap.service.parkinglot.ParkingLotService;
import com.bht.parkingmap.util.LoggingUtil;

import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author bht
 */
@Log4j
@GRpcService
public final class ParkingLotServiceGrpcImpl extends ParkingLotServiceGrpc.ParkingLotServiceImplBase {

    private final ParkingLotService parkingLotService;
    private final ParkingLotMapper parkingLotMapper;

    @Autowired
    public ParkingLotServiceGrpcImpl(ParkingLotService parkingLotService, ParkingLotMapper parkingLotMapper) {
        this.parkingLotService = parkingLotService;
        this.parkingLotMapper = parkingLotMapper;
    }

    @Override
    public void getAllParkingLotCurrentlyWorkingByRadius(ParkingLotScanningByRadius request, StreamObserver<ParkingLotList> responseObserver) {
        try {
            List<ParkingLot> parkingLotList = parkingLotMapper.toParkingLotListWithDistance(
                    parkingLotService.getAllParkingLotCurrentlyWorkingInRegionOfRadius(
                            request.getLatitude(),
                            request.getLongitude(),
                            request.getRadiusToScan()));

            responseObserver.onNext(ParkingLotList.newBuilder().addAllParkingLot(parkingLotList).build());
            responseObserver.onCompleted();

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
            List<ParkingLot> parkingLotList = parkingLotMapper.toParkingLotList(parkingLotService
                    .getAllParkingLotCurrentlyWorkingInRegion(
                            request.getNorthEastLat(),
                            request.getNorthEastLng(),
                            request.getSouthWestLat(),
                            request.getSouthWestLng()));

            responseObserver.onNext(ParkingLotList.newBuilder().addAllParkingLot(parkingLotList).build());
            responseObserver.onCompleted();

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getAllParkingLotCurrentlyWorkingInRegion(%f, %f, %f, %f)",
                            request.getNorthEastLat(), request.getNorthEastLng(), request.getSouthWestLat(), request.getSouthWestLng()));
        }
    }
}