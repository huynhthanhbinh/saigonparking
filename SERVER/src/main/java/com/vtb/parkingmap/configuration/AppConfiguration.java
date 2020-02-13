package com.vtb.parkingmap.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vtb.parkingmap.annotation.InheritedComponent;

/**
 *
 * @author bht
 */
@Configuration
@EnableTransactionManagement
@SuppressWarnings("squid:S1118")
@Import(SpringBeanRegistration.class)
@PropertySource("classpath:parkingmap.properties")
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE, includeFilters = @ComponentScan.Filter(InheritedComponent.class))
public class AppConfiguration {

    static final String BASE_PACKAGE = "com.vtb.parkingmap"; // base package of this project
}