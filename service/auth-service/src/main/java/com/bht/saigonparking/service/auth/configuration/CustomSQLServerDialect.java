package com.bht.saigonparking.service.auth.configuration;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.Type;

/**
 * Custom SQL Server Dialect
 * Extends from Hibernate SQL Server Dialect
 * This class will be called on hibernate init
 * Register all user-defined functions here!
 *
 * @author bht
 */
public final class CustomSQLServerDialect extends SQLServerDialect {

    public CustomSQLServerDialect() {
        super();
        Map<String, Type> userDefinedFunctions = registeredUserDefinedFunctions();
        userDefinedFunctions.forEach((func, type) ->
                registerFunction(func, new StandardSQLFunction(func, type)));
    }

    private Map<String, Type> registeredUserDefinedFunctions() {
        return new HashMap<>();
    }
}