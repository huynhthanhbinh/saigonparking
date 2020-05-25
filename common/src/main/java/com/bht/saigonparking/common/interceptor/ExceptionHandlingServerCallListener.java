package com.bht.saigonparking.common.interceptor;

import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;

/**
 *
 * @author bht
 */
final class ExceptionHandlingServerCallListener<ReqT, RespT>
        extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {

    private final ServerCall<ReqT, RespT> serverCall;
    private final Metadata metadata;

    ExceptionHandlingServerCallListener(ServerCall.Listener<ReqT> listener,
                                        ServerCall<ReqT, RespT> serverCall,
                                        Metadata metadata) {
        super(listener);
        this.serverCall = serverCall;
        this.metadata = metadata;
    }

    @Override
    public void onHalfClose() {
        try {
            super.onHalfClose();

        } catch (RuntimeException exception) {

            handleException(exception, serverCall, metadata);
            throw exception;
        }
    }

    @Override
    public void onReady() {
        try {
            super.onReady();

        } catch (RuntimeException exception) {

            handleException(exception, serverCall, metadata);
            throw exception;
        }
    }

    private void handleException(Exception exception, ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
        if (exception instanceof IllegalArgumentException) {
            serverCall.close(Status.INVALID_ARGUMENT
                    .withDescription(exception.getMessage()), metadata);

        } else {
            serverCall.close(Status.UNKNOWN
                    .withDescription(exception.getMessage()), metadata);
        }
    }
}