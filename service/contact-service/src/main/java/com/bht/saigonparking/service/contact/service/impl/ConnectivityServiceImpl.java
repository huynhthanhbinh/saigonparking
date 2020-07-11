package com.bht.saigonparking.service.contact.service.impl;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.common.constant.SaigonParkingMessageQueue;
import com.bht.saigonparking.service.contact.service.ConnectivityService;
import com.bht.saigonparking.service.contact.service.QueueService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class ConnectivityServiceImpl implements ConnectivityService {

    private final QueueService queueService;

    @Override
    public boolean isUserOnline(@NotNull Long userId) {
        return queueService.isQueueExist(SaigonParkingMessageQueue.getUserQueueName(userId));
    }

    @Override
    public boolean isParkingLotOnline(@NotNull Long parkingLotId) {
        return queueService.isExchangeExist(SaigonParkingMessageQueue.getParkingLotExchangeName(parkingLotId));
    }
}