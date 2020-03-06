package com.bht.parkingmap.webserver.service.grpc;

import static com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceImplBase;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bht.parkingmap.api.proto.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotList;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotScanningByRadius;
import com.bht.parkingmap.api.proto.parkinglot.ParkingLotServiceGrpc.ParkingLotServiceBlockingStub;
import com.bht.parkingmap.webserver.util.LoggingUtil;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;

/**
 *
 * @author bht
 */
@Transactional
@GRpcService
public class ParkingLotServiceGrpcImpl extends ParkingLotServiceImplBase {

    private final ParkingLotServiceBlockingStub parkingLotServiceBlockingStub;

    @Autowired
    public ParkingLotServiceGrpcImpl(ParkingLotServiceBlockingStub parkingLotServiceBlockingStub) {
        this.parkingLotServiceBlockingStub = parkingLotServiceBlockingStub;
    }


    @Override
    public void getTopParkingLotInRegionOrderByDistanceWithName(ParkingLotScanningByRadius request, StreamObserver<ParkingLotList> responseObserver) {
        try {
            ParkingLotList parkingLotList = parkingLotServiceBlockingStub
                    .getTopParkingLotInRegionOrderByDistanceWithName(request);

            responseObserver.onNext(parkingLotList);
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
            ParkingLotList parkingLotList = parkingLotServiceBlockingStub
                    .getTopParkingLotInRegionOrderByDistanceWithoutName(request);

            responseObserver.onNext(parkingLotList);
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