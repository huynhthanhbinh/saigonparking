package com.vtb.parkingmap.configuration;

import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.vtb.parkingmap.base.BaseBean;
import com.vtb.parkingmap.util.LoggingUtil;

import lombok.extern.log4j.Log4j;

/**
 *
 * @author bht
 */
@Log4j
@Component
public final class SpringBeanLifeCycle implements BaseBean, DestructionAwareBeanPostProcessor {


    @Override
    public void initialize() {
        log.info(LoggingUtil.format("SPRING", "BeanCreation", "springBeanLifeCycle"));
    }


    @Override
    public void destroy() {
        log.info(LoggingUtil.format("SPRING", "BeanDestruction", "springBeanLifeCycle"));
    }


    /**
     * such as @PostConstruct using on each single bean,
     * but this is common using for every beans
     * lifecycle: run before @PostConstruct
     * @see com.vtb.parkingmap.base.BaseBean
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) {
        if (bean.getClass().getPackage().getName().startsWith(AppConfiguration.BASE_PACKAGE)) {
            log.info(LoggingUtil.format("SPRING", "BeanCreation", beanName));
        }
        return DestructionAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }


    /**
     * such as @PreDestroy using on each single bean,
     * but this is common using for every beans
     * lifecycle: run before @PreDestroy
     * @see com.vtb.parkingmap.base.BaseBean
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, @NonNull String beanName) {
        if (bean.getClass().getPackage().getName().startsWith(AppConfiguration.BASE_PACKAGE)) {
            log.info(LoggingUtil.format("SPRING", "BeanDestruction", beanName));
        }
    }
}