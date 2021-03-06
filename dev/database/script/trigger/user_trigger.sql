-- noinspection SqlNoDataSourceInspectionForFile
-- noinspection SqlDialectInspectionForFile
-- noinspection SqlResolveForFile
-- @author: bht

USE [USER]
GO
/****** Object:  Trigger [TG_UPDATE_CUSTOMER]    Script Date: 6/24/2020 11:50:35 PM ******/
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
ALTER TABLE [dbo].[CUSTOMER] ENABLE TRIGGER [TG_UPDATE_CUSTOMER]
GO