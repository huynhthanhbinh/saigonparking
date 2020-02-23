package com.bht.parkingmap.service.grpc;

import org.apache.log4j.Level;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.bht.parkingmap.api.parkinglot.ParkingLotInformation;
import com.bht.parkingmap.api.parkinglot.ParkingLotInformationServiceGrpc;
import com.bht.parkingmap.mapper.parkinglot.ParkingLotInformationMapper;
import com.bht.parkingmap.service.parkinglot.ParkingLotInformationService;
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
public final class ParkingLotInformationServiceGrpcImpl extends ParkingLotInformationServiceGrpc.ParkingLotInformationServiceImplBase {

    private final ParkingLotInformationService parkingLotInformationService;
    private final ParkingLotInformationMapper parkingLotInformationMapper;

    @Autowired
    public ParkingLotInformationServiceGrpcImpl(ParkingLotInformationService parkingLotInformationService, ParkingLotInformationMapper parkingLotInformationMapper) {
        this.parkingLotInformationService = parkingLotInformationService;
        this.parkingLotInformationMapper = parkingLotInformationMapper;
    }

    @Override
    public void getParkingLotInformationByParkingLotId(Int64Value request, StreamObserver<ParkingLotInformation> responseObserver) {
        try {
            ParkingLotInformation parkingLotInformation = parkingLotInformationMapper.toParkingLotInformation(
                    parkingLotInformationService.getParkingLotInformationById(request.getValue()));

            responseObserver.onNext(parkingLotInformation);
            responseObserver.onCompleted();

        } catch (Exception exception) {

            responseObserver.onError(exception);
            responseObserver.onCompleted();

            LoggingUtil.log(Level.ERROR, "SERVICE", "Exception", exception.getMessage());
            LoggingUtil.log(Level.WARN, "SERVICE", "Session FAIL",
                    String.format("getParkingLotInformationByParkingLotId(%d)", request.getValue()));
        }
    }
}