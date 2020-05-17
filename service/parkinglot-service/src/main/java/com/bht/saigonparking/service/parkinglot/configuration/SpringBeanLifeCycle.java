package com.bht.saigonparking.service.parkinglot.configuration;

import java.lang.reflect.Proxy;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.service.parkinglot.base.BaseBean;
import com.bht.saigonparking.service.parkinglot.util.LoggingUtil;

/**
 *
 * @author bht
 */
@Component
public final class SpringBeanLifeCycle implements BaseBean, DestructionAwareBeanPostProcessor {


    @Override
    public void initialize() {
        BaseBean.super.initialize();
        LoggingUtil.log(Level.DEBUG, "SPRING", "BeanCreation", "springBeanLifeCycle");
    }


    @Override
    public void destroy() {
        BaseBean.super.destroy();
        LoggingUtil.log(Level.DEBUG, "SPRING", "BeanDestruction", "springBeanLifeCycle");
    }


    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) {
        if (!(bean instanceof Proxy) && bean.getClass().getPackage().getName().startsWith(AppConfiguration.BASE_PACKAGE)) {
            LoggingUtil.log(Level.DEBUG, "SPRING", "BeanCreation", beanName);
        }
        return DestructionAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }


    @Override
    public void postProcessBeforeDestruction(@NonNull Object bean, @NonNull String beanName) {
        if (!(bean instanceof Proxy) && bean.getClass().getPackage().getName().startsWith(AppConfiguration.BASE_PACKAGE)) {
            LoggingUtil.log(Level.DEBUG, "SPRING", "BeanDestruction", beanName);
        }
    }
}