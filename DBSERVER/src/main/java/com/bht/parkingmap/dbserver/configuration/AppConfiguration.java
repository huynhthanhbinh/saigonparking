package com.bht.parkingmap.dbserver.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bht.parkingmap.dbserver.annotation.InheritedComponent;

/**
 *
 * @author bht
 */
@Configuration
@EnableTransactionManagement
@SuppressWarnings("squid:S1118")
@Import(AwsConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE_SERVER,
        includeFilters = @ComponentScan.Filter(InheritedComponent.class))
public class AppConfiguration {

    public static final String BASE_PACKAGE_SERVER = "com.bht.parkingmap.dbserver"; // base package of DBSERVER module
}