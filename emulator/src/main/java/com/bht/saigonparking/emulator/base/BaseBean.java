package com.bht.saigonparking.emulator.base;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author bht
 */
public interface BaseBean {


    @PostConstruct
    default void initialize() {
    }

    @PreDestroy
    default void destroy() {
    }
}