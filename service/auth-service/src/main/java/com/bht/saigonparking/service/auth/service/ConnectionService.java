package com.bht.saigonparking.service.auth.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bht.saigonparking.api.grpc.user.UserServiceGrpc;

import io.grpc.ManagedChannel;

/**
 *
 * @author bht
 */
public interface ConnectionService {

    ManagedChannel createChannelOfService(@NotEmpty String serviceId);

    UserServiceGrpc.UserServiceStub getUserServiceStub(@NotNull ManagedChannel channel);

    UserServiceGrpc.UserServiceBlockingStub getUserServiceBlockingStub(@NotNull ManagedChannel channel);
}