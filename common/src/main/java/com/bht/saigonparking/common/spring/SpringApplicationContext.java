package com.bht.saigonparking.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 *
 * @author bht
 */
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