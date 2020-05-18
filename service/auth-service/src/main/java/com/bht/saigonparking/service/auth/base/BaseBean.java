package com.bht.saigonparking.service.auth.base;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author bht
 */
public interface BaseBean {


    @PostConstruct
    default void initialize() throws IOException {
    }

    @PreDestroy
    default void destroy() {
    }
}