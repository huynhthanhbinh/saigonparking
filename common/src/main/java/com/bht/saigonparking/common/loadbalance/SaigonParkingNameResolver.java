package com.bht.saigonparking.common.loadbalance;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.bht.saigonparking.common.util.LoggingUtil;

import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import lombok.Getter;

/**
 *
 * @author bht
 */
@Getter
public final class SaigonParkingNameResolver extends NameResolver {

    private final URI consulURI;
    private final String serviceId;
    private final DiscoveryClient discoveryClient;

    private Listener listener;
    private List<ServiceInstance> serviceInstances;

    public SaigonParkingNameResolver(DiscoveryClient discoveryClient,
                                     URI consulURI,
                                     String serviceId,
                                     int pauseInSeconds) {

        this.consulURI = consulURI;
        this.serviceId = serviceId;
        this.discoveryClient = discoveryClient;

        /* run connection check timer */
        ConnectionCheckTimer connectionCheckTimer = new ConnectionCheckTimer(this, pauseInSeconds);
        connectionCheckTimer.runTimer();
    }

    @Override
    public String getServiceAuthority() {
        return consulURI.getAuthority();
    }

    @Override
    public void start(Listener2 listener) {
        this.listener = listener;
        loadServiceInstances();
    }

    @Override
    public void shutdown() {
        // implement shutdown...
    }

    void loadServiceInstances() {

        List<EquivalentAddressGroup> addressList = new ArrayList<>();
        serviceInstances = discoveryClient.getInstances(serviceId);

        if (serviceInstances == null || serviceInstances.isEmpty()) {
            LoggingUtil.log(Level.WARN, "loadServiceInstances", "Warning",
                    String.format("no serviceInstances of %s", serviceId));
            return;
        }

        serviceInstances.forEach(serviceInstance -> {
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();

            LoggingUtil.log(Level.INFO, "loadServiceInstances", serviceId, String.format("%s:%d", host, port));

            List<SocketAddress> socketAddressList = new ArrayList<>();
            socketAddressList.add(new InetSocketAddress(host, port));
            addressList.add(new EquivalentAddressGroup(socketAddressList));
        });

        if (!addressList.isEmpty()) {
            listener.onAddresses(addressList, Attributes.EMPTY);
        }
    }
}