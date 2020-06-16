package com.bht.saigonparking.common.loadbalance;

import java.net.URI;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import lombok.AllArgsConstructor;

/**
 * @author bht
 */
@AllArgsConstructor
public final class SaigonParkingNameResolverProvider extends NameResolverProvider {

    private final String serviceId;
    private final DiscoveryClient discoveryClient;

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }

    @Override
    public String getDefaultScheme() {
        return "consul";
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        return new SaigonParkingNameResolver(discoveryClient, targetUri, serviceId);
    }
}