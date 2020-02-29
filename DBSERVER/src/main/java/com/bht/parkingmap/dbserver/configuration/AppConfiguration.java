package com.bht.parkingmap.dbserver.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
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
@Import(MessageQueueConfiguration.class)
@EntityScan(basePackages = AppConfiguration.BASE_PACKAGE_ENTITY)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE_SERVER,
        includeFilters = @ComponentScan.Filter(InheritedComponent.class))
public class AppConfiguration {

    static final String BASE_PACKAGE_SERVER = "com.bht.parkingmap.dbserver";         // base package of SERVER module, contains all
    static final String BASE_PACKAGE_ENTITY = "com.bht.parkingmap.dbserver.entity";  // base package which contains entities definition
    public static final String RESOURCE_DIR = System.getenv("PARKINGMAP_RESOURCES_DIR"); // config env variable first !!!
}