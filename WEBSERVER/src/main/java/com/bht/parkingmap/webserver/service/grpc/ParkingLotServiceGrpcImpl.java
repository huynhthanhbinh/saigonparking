package com.bht.parkingmap.webserver.service.grpc;

import static com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceImplBase;

import java.util.List;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLot;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotList;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotScanningByRadius;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotScanningInRegion;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceBlockingStub;
import com.bht.parkingmap.webserver.util.LoggingUtil;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author bht
 */
@GRpcService
public class ParkingLotServiceGrpcImpl extends ParkingLotServiceImplBase {

    private final ParkingLotServiceBlockingStub parkingLotServiceBlockingStub;

    @Autowired
    public ParkingLotServiceGrpcImpl(ParkingLotServiceBlockingStub parkingLotServiceBlockingStub) {
        this.parkingLotServiceBlockingStub = parkingLotServiceBlockingStub;
    }


    @Override
    public void getAllParkingLotCurrentlyWorkingByRadius(ParkingLotScanningByRadius request, StreamObserver<ParkingLotList> responseObserver) {
        try {
            List<ParkingLot> parkingLotList = parkingLotServiceBlockingStub
                    .getAllParkingLotCurrentlyWorkingByRadius(request)
                    .getParkingLotList();

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
            List<ParkingLot> parkingLotList = parkingLotServiceBlockingStub
                    .getAllParkingLotCurrentlyWorkingInRegion(request)
                    .getParkingLotList();

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
            ParkingLotInformation parkingLotInformation = parkingLotServiceBlockingStub
                    .getParkingLotInformationByParkingLotId(request);

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