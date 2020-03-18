package com.bht.parkingmap.dbserver.configuration;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
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

        Map<String, Type> functions = new HashMap<>();
        functions.put("dbo.CALCULATE_DELTA_LAT_IN_DEGREE", StandardBasicTypes.DOUBLE);
        functions.put("dbo.CALCULATE_DELTA_LNG_IN_DEGREE", StandardBasicTypes.DOUBLE);
        functions.put("dbo.GET_DISTANCE_IN_KILOMETRE", StandardBasicTypes.DOUBLE);
        functions.put("dbo.IS_VALUE_IN_BOUND", StandardBasicTypes.BOOLEAN);
        functions.put("dbo.CHECK_AVAILABILITY", StandardBasicTypes.BOOLEAN);

        return functions;
    }
}