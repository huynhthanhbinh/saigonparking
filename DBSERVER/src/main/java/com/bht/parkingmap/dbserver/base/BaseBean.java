package com.bht.parkingmap.dbserver.base;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Parking Map Project Base Bean
 *
 * @author bht
 */
public interface BaseBean {

    /**
     * This method will be called
     * as the bean has been initialized
     */
    @PostConstruct
    default void initialize() {
    }

    /**
     * This method will be called
     * as the bean is about to be destroyed
     */
    @PreDestroy
    default void destroy() {
    }
}