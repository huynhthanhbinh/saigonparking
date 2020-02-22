package com.bht.parkingmap.service.grpc;

import org.lognet.springboot.grpc.GRpcService;

import com.bht.parkingmap.api.parkinglot.ParkingLotList;
import com.bht.parkingmap.api.parkinglot.ParkingLotScanningByRadius;
import com.bht.parkingmap.api.parkinglot.ParkingLotScanningInRegion;
import com.bht.parkingmap.api.parkinglot.ParkingLotServiceGrpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author bht
 */
@Log4j
@GRpcService
public final class ParkingLotServiceGrpcImpl extends ParkingLotServiceGrpc.ParkingLotServiceImplBase {

    @Override
    public void getAllParkingLotCurrentlyWorkingByRadius(ParkingLotScanningByRadius request, StreamObserver<ParkingLotList> responseObserver) {
        super.getAllParkingLotCurrentlyWorkingByRadius(request, responseObserver);
    }

    @Override
    public void getAllParkingLotCurrentlyWorkingInRegion(ParkingLotScanningInRegion request, StreamObserver<ParkingLotList> responseObserver) {
        super.getAllParkingLotCurrentlyWorkingInRegion(request, responseObserver);
    }
}