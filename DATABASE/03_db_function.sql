-- noinspection SqlResolveForFile

USE [PARKINGMAP]
GO
/****** Object:  UserDefinedFunction [GET_DISTANCE_IN_KILOMETRE]    Script Date: 2/28/2020 9:09:58 PM ******/
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
    SET @KILOMETRE = 6371 * ATAN(SQRT(1 - SQUARE(@ANS)) / @ANS)

    RETURN @KILOMETRE
END
GO