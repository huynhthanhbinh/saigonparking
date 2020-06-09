package com.bht.saigonparking.common.interceptor;

import java.util.Map;

import org.apache.logging.log4j.Level;

import com.bht.saigonparking.common.util.LoggingUtil;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;

/**
 *
 * @author bht
 */
public final class SaigonParkingCustomizedServerCall<ReqT, RespT> extends ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> {

    private final Map<Class<? extends Throwable>, String> errorCodeMap;

    public SaigonParkingCustomizedServerCall(ServerCall<ReqT, RespT> serverCall,
                                             Map<Class<? extends Throwable>, String> errorCodeMap) {
        super(serverCall);
        this.errorCodeMap = errorCodeMap;
    }

    @Override
    public final void close(Status status, Metadata trailers) {
        if (status.getCode() == Status.Code.UNKNOWN
                && status.getDescription() == null
                && status.getCause() != null && errorCodeMap.containsKey(status.getCause().getClass())) {

            Throwable e = status.getCause();
            LoggingUtil.log(Level.ERROR, "ServerInterceptor", "Exception", e.getClass().getSimpleName());
            status = Status.INTERNAL.withDescription(errorCodeMap.get(e.getClass()));
        }
        super.close(status, trailers);
    }
}