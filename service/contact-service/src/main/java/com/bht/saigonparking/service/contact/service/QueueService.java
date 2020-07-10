package com.bht.saigonparking.service.contact.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author bht
 */
public interface QueueService {

    void registerAutoDeleteQueueAndExchangeForUser(@NotNull Long userId, @NotEmpty String userRole);
}