package com.bht.parkingmap.configuration;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author bht
 */
@Component
@SuppressWarnings("squid:CommentedOutCodeLine")
public final class SpringBeanRegistration {

    /**
     * In Spring, we can use annotation @PropertySource
     * to externalize our configurations to
     * a properties file under src/main/resources folder
     * Therefore, we can get these values through
     * <code>@Value</code> annotation or Environment object(@Autowired)
     * For eg. @Value("${mssql.url}") private String url
     */
    private final Environment environment;

    public SpringBeanRegistration(Environment environment) {
        this.environment = environment;
    }


    /**
     * As Environment obj is in the same class
     * Configurer method need to be STATIC one
     * As environment object can access directly
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * DataSource / Database Configuration
     * Using properties file in src/main/resources
     * <code>@PropertySource("classpath:/pim.properties")</code>
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects
                .requireNonNull(environment.getProperty("mssql.DriverClassName")));
        dataSource.setUrl(environment.getProperty("mssql.Url"));
        dataSource.setUsername(environment.getProperty("mssql.Username"));
        dataSource.setPassword(environment.getProperty("mssql.Password"));

        //  to enable Prepared Statement Handle Caching in the mssql jdbc driver
        Properties jdbcProperties = new Properties();
        jdbcProperties.put("disableStatementPooling", Objects
                .requireNonNull(environment.getProperty("mssql.disableStatementPooling")));
        jdbcProperties.put("statementPoolingCacheSize", Objects
                .requireNonNull(environment.getProperty("mssql.statementPoolingCacheSize")));
        dataSource.setConnectionProperties(jdbcProperties);

        return dataSource;
    }


    /**
     * Config Session Factory / Hibernate
     */
    @Bean
    // @DependsOn("dataSource")
    public LocalSessionFactoryBean sessionFactoryBean(@Autowired DataSource dataSource) {
        // Session Factory Configure
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();

        // DataSource as learnt before:
        // eg. MSSQL Config, DB: BHT, localhost
        // bean.setDataSource(SpringApplicationContext.getBean(DataSource.class));
        bean.setDataSource(dataSource);

        // Package contains class mapping record to model
        // eg. com.bht.pim.entities
        bean.setPackagesToScan(environment.getProperty("packages.entities"));

        // Hibernate Properties for hibernate extra config
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect",
                Objects.requireNonNull(environment.getProperty("hibernate.dialect")));
        hibernateProperties.put("hibernate.show_sql",
                Objects.requireNonNull(environment.getProperty("hibernate.show_sql")));
        hibernateProperties.put("hibernate.connection.pool_size",
                Objects.requireNonNull(environment.getProperty("hibernate.connection.pool_size")));
        hibernateProperties.put("hibernate.connection.autocommit",
                Objects.requireNonNull(environment.getProperty("hibernate.connection.autocommit")));
        hibernateProperties.put("hibernate.legacy_limit_handler",
                Objects.requireNonNull(environment.getProperty("hibernate.legacy_limit_handler"))); //pagination

        // Assign hibernateProperties to SessionFactory Config
        bean.setHibernateProperties(hibernateProperties);

        return bean;
    }

    /**
     * For handling transactions / connections
     * Spring support @EnableTransactionManagement
     * Transaction use in Service Class, DAO Class
     * not using for Controller !!! meaningless !!
     * 2 ways of using TX:
     * + Transaction for the whole class service/DAO
     * + Transaction for each unit method inside service/DAO
     * We can have multi different bean transaction manager
     * Therefore, we need to declare exactly the bean name
     */
    @Bean("transactionManager")
    public HibernateTransactionManager hibernateTransactionManager(@Autowired SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        hibernateTransactionManager.setRollbackOnCommitFailure(true);
        return hibernateTransactionManager;
    }
}