-- noinspection SqlNoDataSourceInspectionForFile
-- noinspection SqlDialectInspectionForFile
-- noinspection SqlResolveForFile
-- @author: bht

USE [BOOKING]
GO
/****** Object:  Table [BOOKING]    Script Date: 7/13/2020 2:42:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BOOKING](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PARKING_LOT_ID] [bigint] NOT NULL,
	[CUSTOMER_ID] [bigint] NOT NULL,
	[CUSTOMER_LICENSE_PLATE] [nvarchar](30) NOT NULL,
	[IS_FINISHED] [bit] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_BOOKING] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [BOOKING_HISTORY]    Script Date: 7/13/2020 2:42:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BOOKING_HISTORY](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[BOOKING_ID] [bigint] NOT NULL,
	[STATUS_ID] [bigint] NOT NULL,
	[LAST_UPDATED] [smalldatetime] NOT NULL,
	[NOTE] [nvarchar](255) NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_BOOKING_HISTORY] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [BOOKING_STATUS]    Script Date: 7/13/2020 2:42:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [BOOKING_STATUS](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[STATUS] [nvarchar](30) NOT NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_BOOKING_STATUS] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [BOOKING] ADD  CONSTRAINT [DF_BOOKING_IS_FINISHED]  DEFAULT ((0)) FOR [IS_FINISHED]
GO
ALTER TABLE [BOOKING] ADD  CONSTRAINT [DF_BOOKING_VERSION]  DEFAULT ((1)) FOR [VERSION]
GO
ALTER TABLE [BOOKING_HISTORY] ADD  CONSTRAINT [DF_BOOKING_HISTORY_NOTE]  DEFAULT ('') FOR [NOTE]
GO
ALTER TABLE [BOOKING_HISTORY] ADD  CONSTRAINT [DF_BOOKING_HISTORY_VERSION]  DEFAULT ((1)) FOR [VERSION]
GO
ALTER TABLE [BOOKING_STATUS] ADD  CONSTRAINT [DF_BOOKING_STATUS_VERSION]  DEFAULT ((1)) FOR [VERSION]
GO
ALTER TABLE [BOOKING_HISTORY]  WITH CHECK ADD  CONSTRAINT [FK_BOOKING_HISTORY_BOOKING] FOREIGN KEY([BOOKING_ID])
REFERENCES [BOOKING] ([ID])
GO
ALTER TABLE [BOOKING_HISTORY] CHECK CONSTRAINT [FK_BOOKING_HISTORY_BOOKING]
GO
ALTER TABLE [BOOKING_HISTORY]  WITH CHECK ADD  CONSTRAINT [FK_BOOKING_HISTORY_BOOKING_STATUS] FOREIGN KEY([STATUS_ID])
REFERENCES [BOOKING_STATUS] ([ID])
GO
ALTER TABLE [BOOKING_HISTORY] CHECK CONSTRAINT [FK_BOOKING_HISTORY_BOOKING_STATUS]
GO
/****** Object:  Trigger [TG_INSERT_BOOKING]    Script Date: 7/13/2020 2:42:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [TG_INSERT_BOOKING]
    ON [BOOKING]
    AFTER INSERT
    AS
BEGIN
    SET NOCOUNT ON

	INSERT INTO BOOKING_HISTORY (BOOKING_ID, STATUS_ID, LAST_UPDATED)
	SELECT I.ID, S.ID, getdate()
	FROM INSERTED I, BOOKING_STATUS S
	WHERE S.STATUS = 'CREATED'
END
GO
ALTER TABLE [dbo].[BOOKING] ENABLE TRIGGER [TG_INSERT_BOOKING]
GO

/****** Object:  Trigger [TG_INSERT_UPDATE_BOOKING_HISTORY]    Script Date: 7/13/2020 2:42:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [TG_INSERT_UPDATE_BOOKING_HISTORY]
    ON [BOOKING_HISTORY]
    AFTER INSERT, UPDATE
    AS
BEGIN
    SET NOCOUNT ON

    UPDATE B
    SET IS_FINISHED = 1
    FROM BOOKING B
    INNER JOIN INSERTED I ON B.ID = I.BOOKING_ID 
	INNER JOIN BOOKING_STATUS S ON S.ID = I.STATUS_ID
	AND (S.STATUS = 'FINISHED' OR S.STATUS = 'CANCELLED' OR S.STATUS = 'REJECTED')
END
GO
ALTER TABLE [dbo].[BOOKING_HISTORY] ENABLE TRIGGER [TG_INSERT_UPDATE_BOOKING_HISTORY]
GO