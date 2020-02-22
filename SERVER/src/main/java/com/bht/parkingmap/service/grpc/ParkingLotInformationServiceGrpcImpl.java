package com.bht.parkingmap.service.grpc;

import org.lognet.springboot.grpc.GRpcService;

import com.bht.parkingmap.api.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.parkinglot.ParkingLotInformationServiceGrpc;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;

import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author bht
 */
@Log4j
@GRpcService
public final class ParkingLotInformationServiceGrpcImpl extends ParkingLotInformationServiceGrpc.ParkingLotInformationServiceImplBase {

    @Override
    public void getParkingLotInformationByParkingLotId(Int64Value request, StreamObserver<ParkingLotInformation> responseObserver) {
        super.getParkingLotInformationByParkingLotId(request, responseObserver);
    }

    @Override
    public void updateParkingLotInformation(ParkingLotInformation request, StreamObserver<BoolValue> responseObserver) {
        super.updateParkingLotInformation(request, responseObserver);
    }
}