package com.bht.saigonparking.service.contact.service;

import javax.validation.constraints.NotNull;

/**
 *
 * @author bht
 */
public interface ConnectivityService {

    boolean isUserOnline(@NotNull Long userId);

    boolean isParkingLotOnline(@NotNull Long parkingLotId);
}