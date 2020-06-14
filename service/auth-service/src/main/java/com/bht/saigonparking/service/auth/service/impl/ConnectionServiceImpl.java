package com.bht.saigonparking.service.auth.service.impl;

import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;
import com.bht.saigonparking.service.auth.service.ConnectionService;
import com.bht.saigonparking.service.auth.service.DiscoveryService;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionServiceImpl implements ConnectionService {

    private final DiscoveryService discoveryService;

    private final SaigonParkingClientInterceptor clientInterceptor;

    @Value("${service.idle-timeout}")
    private Integer timeout;

    @Value("${service.max-inbound-message-size}")
    private Integer maxInBoundMessageSize;

    @Value("${service.max-inbound-metadata-size}")
    private Integer maxInBoundMetadataSize;

    @Override
    public ManagedChannel createChannelOfService(@NotEmpty String serviceId) {
        return NettyChannelBuilder
                .forAddress(discoveryService.getInstanceOfService(serviceId))   // build channel to server with server's address
                .usePlaintext()                                                 // use plain-text to communicate internally
                .keepAliveWithoutCalls(false)                                   // Close channel when client has already received response
                .idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 10000 milliseconds / 1000 = 10 seconds --> request time-out
                .maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
                .maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
                .intercept(clientInterceptor)                                   // add internal credential authentication
                .build();                                                       // Build channel to communicate over gRPC
    }

    @Override
    public UserServiceGrpc.UserServiceStub getUserServiceStub(@NotNull ManagedChannel channel) {
        return UserServiceGrpc.newStub(channel);
    }

    @Override
    public UserServiceGrpc.UserServiceBlockingStub getUserServiceBlockingStub(@NotNull ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }
}