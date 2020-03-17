package com.bht.parkingmap.dbserver.configuration;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * Custom SQL Server Dialect
 * Extends from Hibernate SQL Server Dialect
 * Register all user-defined functions here!
 *
 * @author bht
 */
public final class CustomSQLServerDialect extends SQLServerDialect {

    public CustomSQLServerDialect() {
        super();
        registerFunction("dbo.CALCULATE_DELTA_LAT_IN_DEGREE",
                new StandardSQLFunction("dbo.CALCULATE_DELTA_LAT_IN_DEGREE", StandardBasicTypes.DOUBLE));

        registerFunction("dbo.CALCULATE_DELTA_LNG_IN_DEGREE",
                new StandardSQLFunction("dbo.CALCULATE_DELTA_LNG_IN_DEGREE", StandardBasicTypes.DOUBLE));

        registerFunction("dbo.GET_DISTANCE_IN_KILOMETRE",
                new StandardSQLFunction("dbo.GET_DISTANCE_IN_KILOMETRE", StandardBasicTypes.DOUBLE));

        registerFunction("dbo.IS_VALUE_IN_BOUND",
                new StandardSQLFunction("dbo.IS_VALUE_IN_BOUND", StandardBasicTypes.BOOLEAN));

        registerFunction("dbo.CHECK_AVAILABILITY",
                new StandardSQLFunction("dbo.CHECK_AVAILABILITY", StandardBasicTypes.BOOLEAN));
    }
}