package com.bht.saigonparking.service.parkinglot.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 *
 * This class is used for testing purpose only
 * or used for getting spring bean dynamically
 * via calling method getBean()
 * with 1 param only is bean class,
 * eg. {@code getBean(ParkingLotService.class)}
 *
 * @author bht
 */
@Component
public final class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * get bean created before by app static context
     * @param <T> any object has been injected before
     * @return Bean of a specific class
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     * used by Spring !!!!
     * please don't use it
     */
    @Override
    public synchronized void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        context = applicationContext;
    }
}