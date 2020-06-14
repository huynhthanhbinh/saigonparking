package com.bht.saigonparking.service.auth.service;

import java.net.SocketAddress;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author bht
 */
public interface DiscoveryService {

    SocketAddress getInstanceOfService(@NotEmpty String serviceId);
}