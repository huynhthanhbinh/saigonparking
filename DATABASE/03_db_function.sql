USE [PARKINGMAP]
GO
/****** Object:  UserDefinedFunction [CALCULATE_DELTA_LAT_IN_DEGREE]    Script Date: 3/4/2020 3:55:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [CALCULATE_DELTA_LAT_IN_DEGREE](@LAT FLOAT,
                                                @LNG FLOAT,
                                                @DISTANCE INT)
    RETURNS FLOAT
AS
BEGIN
    RETURN @DISTANCE * 57.29577951 / 6371
END
GO
/****** Object:  UserDefinedFunction [CALCULATE_DELTA_LNG_IN_DEGREE]    Script Date: 3/4/2020 3:55:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [CALCULATE_DELTA_LNG_IN_DEGREE](@LAT FLOAT,
                                                @LNG FLOAT,
                                                @DISTANCE INT)
    RETURNS FLOAT
AS
BEGIN
    RETURN 57.29577951 * ASIN(SIN(CAST(@DISTANCE AS FLOAT) / 6371) / COS(@LAT / 57.29577951))
END
GO
/****** Object:  UserDefinedFunction [GET_DISTANCE_IN_KILOMETRE]    Script Date: 3/4/2020 3:55:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [GET_DISTANCE_IN_KILOMETRE](@LAT1 FLOAT, /* POINT1.LATITUDE */
                                            @LNG1 FLOAT, /* POINT1.LONGITUDE */
                                            @LAT2 FLOAT, /* POINT2.LATITUDE */
                                            @LNG2 FLOAT) /* POINT2.LONGITUDE */
    RETURNS FLOAT
AS
BEGIN
    DECLARE
        @DEGTORAD FLOAT
    DECLARE
        @ANS FLOAT
    DECLARE
        @KILOMETRE FLOAT

    SET @DEGTORAD = 57.29577951
    SET @ANS = 0
    SET @KILOMETRE = 0

    IF @LAT1 IS NULL OR @LAT1 = 0 OR
       @LNG1 IS NULL OR @LNG1 = 0 OR
       @LAT2 IS NULL OR @LAT2 = 0 OR
       @LNG2 IS NULL OR @LNG2 = 0
        BEGIN
            RETURN @KILOMETRE
        END

    SET @ANS = SIN(@LAT1 / @DEGTORAD) * SIN(@LAT2 / @DEGTORAD) +
               COS(@LAT1 / @DEGTORAD) * COS(@LAT2 / @DEGTORAD) * COS(ABS(@LNG2 - @LNG1) / @DEGTORAD)
    SET @KILOMETRE = 6371 * ACOS(@ANS)

    RETURN @KILOMETRE
END
GO
/****** Object:  UserDefinedFunction [IS_VALUE_IN_BOUND]    Script Date: 3/4/2020 3:55:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [IS_VALUE_IN_BOUND](@VALUE FLOAT,
                                    @PIVOT FLOAT,
                                    @DELTA FLOAT)
    RETURNS BIT
AS
BEGIN
    IF @VALUE <= @PIVOT + @DELTA
        AND @VALUE >= @PIVOT - @DELTA
        BEGIN
            RETURN 1
        END
    RETURN 0
END
GO
