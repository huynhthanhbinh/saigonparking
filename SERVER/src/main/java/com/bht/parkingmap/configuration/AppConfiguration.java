package com.bht.parkingmap.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bht.parkingmap.annotation.InheritedComponent;

/**
 *
 * @author bht
 */
@Configuration
@EnableTransactionManagement
@SuppressWarnings("squid:S1118")
@Import(SpringBeanRegistration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE, includeFilters = @ComponentScan.Filter(InheritedComponent.class))
public class AppConfiguration {

    static final String BASE_PACKAGE = "com.bht.parkingmap"; // base package of this project
}