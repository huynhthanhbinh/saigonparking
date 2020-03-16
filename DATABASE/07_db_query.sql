-- noinspection SqlResolveForFile
-- @author: bht

USE PARKINGMAP
GO

DECLARE
    @LATITUDE FLOAT = 10.784122211291663
DECLARE
    @LONGITUDE FLOAT= 106.71152489259839
DECLARE
    @RADIUS INT = 5
DECLARE
    @NROWS INT = 5


-- WITH PARKING-LOT NAME
SELECT P.ID, PLI.NAME, P.PARKING_LOT_TYPE_ID, P.LATITUDE, P.LONGITUDE, PLL.AVAILABILITY, PLL.CAPACITY
FROM PARKING_LOT P
         INNER JOIN (SELECT ID, CAPACITY, AVAILABILITY FROM PARKING_LOT_LIMIT) AS PLL ON PLL.ID = P.ID
         INNER JOIN (SELECT ID, NAME FROM PARKING_LOT_INFORMATION) AS PLI ON PLI.ID = P.ID
    AND 1 =
        DBO.IS_VALUE_IN_BOUND(P.LATITUDE, @LATITUDE, DBO.CALCULATE_DELTA_LAT_IN_DEGREE(@LATITUDE, @LONGITUDE, @RADIUS))
    AND 1 =
        DBO.IS_VALUE_IN_BOUND(P.LONGITUDE, @LONGITUDE,
                              DBO.CALCULATE_DELTA_LNG_IN_DEGREE(@LATITUDE, @LONGITUDE, @RADIUS))
    AND 1 = P.IS_AVAILABLE
    AND CONVERT(TIME, GETDATE()) BETWEEN P.OPENING_HOUR AND P.CLOSING_HOUR
ORDER BY dbo.GET_DISTANCE_IN_KILOMETRE(@LATITUDE, @LONGITUDE, P.LATITUDE, P.LONGITUDE)
    OFFSET 0 ROWS FETCH NEXT @NROWS ROWS ONLY


-- WITHOUT PARKING-LOT NAME
SELECT P.ID, P.PARKING_LOT_TYPE_ID, P.LATITUDE, P.LONGITUDE, PLL.AVAILABILITY, PLL.CAPACITY
FROM PARKING_LOT P
         INNER JOIN (SELECT ID, CAPACITY, AVAILABILITY FROM PARKING_LOT_LIMIT) AS PLL ON PLL.ID = P.ID
    AND 1 =
        DBO.IS_VALUE_IN_BOUND(P.LATITUDE, @LATITUDE, DBO.CALCULATE_DELTA_LAT_IN_DEGREE(@LATITUDE, @LONGITUDE, @RADIUS))
    AND 1 =
        DBO.IS_VALUE_IN_BOUND(P.LONGITUDE, @LONGITUDE,
                              DBO.CALCULATE_DELTA_LNG_IN_DEGREE(@LATITUDE, @LONGITUDE, @RADIUS))
    AND 1 = P.IS_AVAILABLE
    AND CONVERT(TIME, GETDATE()) BETWEEN P.OPENING_HOUR AND P.CLOSING_HOUR
ORDER BY dbo.GET_DISTANCE_IN_KILOMETRE(@LATITUDE, @LONGITUDE, P.LATITUDE, P.LONGITUDE)
    OFFSET 0 ROWS FETCH NEXT @NROWS ROWS ONLY