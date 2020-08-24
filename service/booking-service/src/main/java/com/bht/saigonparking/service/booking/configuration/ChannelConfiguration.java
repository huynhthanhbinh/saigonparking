package com.bht.saigonparking.service.booking.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.parkinglot.ParkingLotServiceGrpc;
import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;
import com.bht.saigonparking.common.interceptor.SaigonParkingClientInterceptor;
import com.bht.saigonparking.common.loadbalance.SaigonParkingNameResolverProvider;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ChannelConfiguration {

    private final SaigonParkingClientInterceptor clientInterceptor;

    @Bean("parkingLotResolver")
    public SaigonParkingNameResolverProvider userServiceNameResolver(@Value("${connection.parkinglot-service.id}") String serviceId,
                                                                     @Autowired DiscoveryClient discoveryClient) {

        return new SaigonParkingNameResolverProvider(serviceId, discoveryClient);
    }

    /**
     *
     * channel is the abstraction to connect to a service endpoint
     *
     * note for gRPC service stub:
     *      .newStub(channel)          --> nonblocking/asynchronous stub
     *      .newBlockingStub(channel)  --> blocking/synchronous stub
     */
    @Bean("parkingLotChannel")
    public ManagedChannel parkingLotChannel(@Value("${spring.cloud.consul.host}") String host,
                                            @Value("${spring.cloud.consul.port}") int port,
                                            @Value("${connection.idle-timeout}") int timeout,
                                            @Value("${connection.max-inbound-message-size}") int maxInBoundMessageSize,
                                            @Value("${connection.max-inbound-metadata-size}") int maxInBoundMetadataSize,
                                            @Value("${connection.load-balancing-policy}") String loadBalancingPolicy,
                                            @Qualifier("parkingLotResolver") SaigonParkingNameResolverProvider nameResolverProvider) {

        return ManagedChannelBuilder
                .forTarget("consul://" + host + ":" + port)                     // build channel to server with server's address
                .keepAliveWithoutCalls(false)                                   // Close channel when client has already received response
                .idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 10000 milliseconds / 1000 = 10 seconds --> request time-out
                .maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
                .maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
                .defaultLoadBalancingPolicy(loadBalancingPolicy)                // set load balancing policy for channel
                .nameResolverFactory(nameResolverProvider)                      // using Consul service discovery for DNS querying
                .intercept(clientInterceptor)                                   // add internal credential authentication
                .usePlaintext()                                                 // use plain-text to communicate internally
                .build();                                                       // Build channel to communicate over gRPC
    }

    /* asynchronous parking-lot service stub */
    @Bean
    public ParkingLotServiceGrpc.ParkingLotServiceStub parkingLotServiceStub(@Qualifier("parkingLotChannel") ManagedChannel channel) {
        return ParkingLotServiceGrpc.newStub(channel);
    }

    /* synchronous parking-lot service stub */
    @Bean
    public ParkingLotServiceGrpc.ParkingLotServiceBlockingStub parkingLotServiceBlockingStub(@Qualifier("parkingLotChannel") ManagedChannel channel) {
        return ParkingLotServiceGrpc.newBlockingStub(channel);
    }

    @Bean("userResolver")
    public SaigonParkingNameResolverProvider userServiceNameResolverProvider(@Value("${connection.user-service.id}") String serviceId,
                                                                             @Autowired DiscoveryClient discoveryClient) {

        return new SaigonParkingNameResolverProvider(serviceId, discoveryClient);
    }

    @Bean("userChannel")
    public ManagedChannel managedChannel(@Value("${spring.cloud.consul.host}") String host,
                                         @Value("${spring.cloud.consul.port}") int port,
                                         @Value("${connection.idle-timeout}") int timeout,
                                         @Value("${connection.max-inbound-message-size}") int maxInBoundMessageSize,
                                         @Value("${connection.max-inbound-metadata-size}") int maxInBoundMetadataSize,
                                         @Value("${connection.load-balancing-policy}") String loadBalancingPolicy,
                                         @Qualifier("userResolver") SaigonParkingNameResolverProvider nameResolverProvider) {

        return ManagedChannelBuilder
                .forTarget("consul://" + host + ":" + port)                     // build channel to server with server's address
                .keepAliveWithoutCalls(false)                                   // Close channel when client has already received response
                .idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 10000 milliseconds / 1000 = 10 seconds --> request time-out
                .maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
                .maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
                .defaultLoadBalancingPolicy(loadBalancingPolicy)                // set load balancing policy for channel
                .nameResolverFactory(nameResolverProvider)                      // using Consul service discovery for DNS querying
                .intercept(clientInterceptor)                                   // add internal credential authentication
                .usePlaintext()                                                 // use plain-text to communicate internally
                .build();                                                       // Build channel to communicate over gRPC
    }

    /* asynchronous user service stub */
    @Bean
    public UserServiceGrpc.UserServiceStub userServiceStub(@Qualifier("userChannel") ManagedChannel channel) {
        return UserServiceGrpc.newStub(channel);
    }

    /* synchronous user service stub */
    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(@Qualifier("userChannel") ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }
}