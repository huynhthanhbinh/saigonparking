package com.bht.saigonparking.service.auth.service.impl;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.common.exception.ServiceUnavailableException;
import com.bht.saigonparking.service.auth.service.DiscoveryService;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiscoveryServiceImpl implements DiscoveryService {

    private final DiscoveryClient discoveryClient;

    @Override
    public SocketAddress getInstanceOfService(@NotEmpty String serviceId) {
        ServiceInstance serviceInstance = discoveryClient
                .getInstances(serviceId)
                .stream()
                .findFirst()
                .orElseThrow(ServiceUnavailableException::new);

        return new InetSocketAddress(serviceInstance.getHost(), serviceInstance.getPort());
    }
}