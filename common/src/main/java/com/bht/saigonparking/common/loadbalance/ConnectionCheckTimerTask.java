package com.bht.saigonparking.common.loadbalance;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;
import org.springframework.cloud.client.ServiceInstance;

import com.bht.saigonparking.common.util.LoggingUtil;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@AllArgsConstructor
final class ConnectionCheckTimerTask extends TimerTask {

    private final SaigonParkingNameResolver saigonParkingNameResolver;

    @Override
    @SuppressWarnings({"java:S2095", "java:S1192"})
    public void run() {
        List<ServiceInstance> serviceInstances = saigonParkingNameResolver.getServiceInstances();

        if (serviceInstances != null && !serviceInstances.isEmpty()) {
            for (ServiceInstance serviceInstance : serviceInstances) {
                try {
                    new Socket(serviceInstance.getHost(), serviceInstance.getPort());

                } catch (IOException e) {

                    LoggingUtil.log(Level.ERROR, "ConnectionCheckTimerTask", "Exception", e.getMessage());
                    LoggingUtil.log(Level.INFO, "ConnectionCheckTimerTask", "Reloading", "serviceInstances being reloaded...");
                    saigonParkingNameResolver.loadServiceInstances();
                    return;
                }
            }

        } else {

            LoggingUtil.log(Level.WARN, "ConnectionCheckTimerTask", "Warning", "no serviceInstances...");
        }
    }
}