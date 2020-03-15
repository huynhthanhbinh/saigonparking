-- noinspection SqlResolveForFile
-- @author: bht
USE [PARKINGMAP]
GO

/****** Object:  Trigger [TG_UPDATE_CUSTOMER]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [TG_UPDATE_CUSTOMER]
    ON [CUSTOMER]
    FOR UPDATE
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE C
    SET LAST_UPDATED = GETDATE()
    FROM CUSTOMER C
             INNER JOIN INSERTED I
                        ON C.ID = I.ID
END
GO
ALTER TABLE [dbo].[CUSTOMER]
    ENABLE TRIGGER [TG_UPDATE_CUSTOMER]
GO
/****** Object:  Trigger [TG_UPDATE_PARKING_LOT]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [TG_UPDATE_PARKING_LOT]
    ON [PARKING_LOT]
    FOR UPDATE
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE PL
    SET IS_AVAILABLE = 0
    FROM PARKING_LOT PL
             INNER JOIN INSERTED I
                        ON PL.ID = I.ID AND I.AVAILABILITY = 0

    UPDATE PL
    SET IS_AVAILABLE = 1
    FROM PARKING_LOT PL
             INNER JOIN INSERTED I
                        ON PL.ID = I.ID AND I.AVAILABILITY > 0
END
GO
ALTER TABLE [dbo].[PARKING_LOT]
    ENABLE TRIGGER [TG_UPDATE_PARKING_LOT]
GO
/****** Object:  Trigger [TG_CREATE_PARKING_LOT_RATING]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [TG_CREATE_PARKING_LOT_RATING]
    ON [PARKING_LOT_RATING]
    AFTER INSERT
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE PLI
    SET RATING_AVERAGE   = ((RATING_AVERAGE * NUMBER_OF_RATING) + I.RATING) / (NUMBER_OF_RATING + 1),
        NUMBER_OF_RATING = NUMBER_OF_RATING + 1

    FROM PARKING_LOT_INFORMATION PLI
             INNER JOIN INSERTED I ON I.PARKING_LOT_ID = PLI.ID
END
GO
ALTER TABLE [dbo].[PARKING_LOT_RATING]
    ENABLE TRIGGER [TG_CREATE_PARKING_LOT_RATING]
GO
/****** Object:  Trigger [TG_DELETE_PARKING_LOT_RATING]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [TG_DELETE_PARKING_LOT_RATING]
    ON [PARKING_LOT_RATING]
    FOR DELETE
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE PLI
    SET RATING_AVERAGE   = ((RATING_AVERAGE * NUMBER_OF_RATING) - D.RATING) / (NUMBER_OF_RATING - 1),
        NUMBER_OF_RATING = NUMBER_OF_RATING - 1

    FROM PARKING_LOT_INFORMATION PLI
             INNER JOIN DELETED D ON D.PARKING_LOT_ID = PLI.ID
END
GO
ALTER TABLE [dbo].[PARKING_LOT_RATING]
    ENABLE TRIGGER [TG_DELETE_PARKING_LOT_RATING]
GO
/****** Object:  Trigger [TG_UPDATE_PARKING_LOT_RATING]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [TG_UPDATE_PARKING_LOT_RATING]
    ON [PARKING_LOT_RATING]
    FOR UPDATE
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE PLI
    SET RATING_AVERAGE = ((RATING_AVERAGE * NUMBER_OF_RATING) - D.RATING + I.RATING) / NUMBER_OF_RATING

    FROM PARKING_LOT_INFORMATION PLI
             INNER JOIN (SELECT PARKING_LOT_ID, RATING FROM DELETED) AS D ON D.PARKING_LOT_ID = PLI.ID
             INNER JOIN (SELECT PARKING_LOT_ID, RATING FROM INSERTED) AS I ON I.PARKING_LOT_ID = PLI.ID

    UPDATE PLR
    SET LAST_UPDATED = GETDATE()
    FROM PARKING_LOT_RATING PLR
             INNER JOIN INSERTED I ON I.ID = PLR.ID
END
GO
ALTER TABLE [dbo].[PARKING_LOT_RATING]
    ENABLE TRIGGER [TG_UPDATE_PARKING_LOT_RATING]
GO
/****** Object:  Trigger [TG_UPDATE_PARKING_LOT_SUGGESTION]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [TG_UPDATE_PARKING_LOT_SUGGESTION]
    ON [PARKING_LOT_SUGGESTION]
    FOR UPDATE
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE PLS
    SET LAST_UPDATED = GETDATE()
    FROM PARKING_LOT_SUGGESTION PLS
             INNER JOIN INSERTED I
                        ON PLS.ID = I.ID
END
GO
ALTER TABLE [dbo].[PARKING_LOT_SUGGESTION]
    ENABLE TRIGGER [TG_UPDATE_PARKING_LOT_SUGGESTION]
GO
/****** Object:  Trigger [TG_UPDATE_PARKING_LOT_UNIT]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [TG_UPDATE_PARKING_LOT_UNIT]
    ON [PARKING_LOT_UNIT]
    FOR UPDATE
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE PLU
    SET LAST_UPDATED = GETDATE()
    FROM PARKING_LOT_UNIT PLU
             INNER JOIN INSERTED I
                        ON PLU.ID = I.ID
END
GO
ALTER TABLE [dbo].[PARKING_LOT_UNIT]
    ENABLE TRIGGER [TG_UPDATE_PARKING_LOT_UNIT]
GO