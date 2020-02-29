package com.bht.parkingmap.webserver.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author bht
 */
@Configuration
@SuppressWarnings("squid:S1118")
@Import(MessageQueueConfiguration.class)
@ComponentScan(basePackages = AppConfiguration.BASE_PACKAGE_SERVER)
public class AppConfiguration {

    static final String BASE_PACKAGE_SERVER = "com.bht.parkingmap.webserver"; // base package of SERVER module, contains all
}