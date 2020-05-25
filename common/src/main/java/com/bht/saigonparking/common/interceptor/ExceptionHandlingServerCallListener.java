package com.bht.saigonparking.common.interceptor;

import org.apache.logging.log4j.Level;

import com.bht.saigonparking.common.util.LoggingUtil;

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
    private final StackTraceElement[] emptyStackTrace = new StackTraceElement[0];

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

            LoggingUtil.log(Level.ERROR, "gRPC onHalfClose", "Exception", exception.getMessage());
            handleException(exception, serverCall, metadata);
            exception.setStackTrace(emptyStackTrace);
            throw exception;
        }
    }

    @Override
    public void onReady() {
        try {
            super.onReady();

        } catch (RuntimeException exception) {

            LoggingUtil.log(Level.ERROR, "gRPC onReady", "Exception", exception.getMessage());
            handleException(exception, serverCall, metadata);
            exception.setStackTrace(emptyStackTrace);
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