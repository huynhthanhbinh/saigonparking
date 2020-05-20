package com.bht.saigonparking.common.base;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Saigon Parking Project Base Bean
 *
 * @author bht
 */
public interface BaseBean {

    /**
     * This method will be called
     * as the bean has been initialized
     */
    @PostConstruct
    default void initialize() throws IOException {
    }

    /**
     * This method will be called
     * as the bean is about to be destroyed
     */
    @PreDestroy
    default void destroy() {
    }
}