-- noinspection SqlResolveForFile
-- @author: bht
USE [PARKINGMAP]
GO
/****** Object:  Table [CUSTOMER]    Script Date: 3/8/2020 3:15:17 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CUSTOMER]
(
    [ID]           [bigint]        NOT NULL,
    [FIRST_NAME]   [nvarchar](20)  NOT NULL,
    [LAST_NAME]    [nvarchar](40)  NOT NULL,
    [PHONE]        [nvarchar](15)  NOT NULL,
    [LAST_UPDATED] [smalldatetime] NULL,
    CONSTRAINT [PK_CUSTOMER] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT]    Script Date: 3/8/2020 3:15:17 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT]
(
    [ID]                  [bigint] IDENTITY (1,1) NOT NULL,
    [PARKING_LOT_TYPE_ID] [bigint]                NOT NULL,
    [LATITUDE]            [float]                 NOT NULL,
    [LONGITUDE]           [float]                 NOT NULL,
    [OPENING_HOUR]        [time](7)               NOT NULL,
    [CLOSING_HOUR]        [time](7)               NOT NULL,
    [CAPACITY]            [smallint]              NULL,
    [AVAILABILITY]        [smallint]              NULL,
    [IS_AVAILABLE]        [bit]                   NULL,
    [VERSION]             [bigint]                NULL,
    CONSTRAINT [PK_PARKING_LOT] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_EMPLOYEE]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_EMPLOYEE]
(
    [ID]             [bigint] NOT NULL,
    [PARKING_LOT_ID] [bigint] NOT NULL,
    CONSTRAINT [PK_PARKING_LOT_EMPLOYEE] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
    CONSTRAINT [CK_PARKING_LOT_UNIQUE] UNIQUE NONCLUSTERED
        (
         [PARKING_LOT_ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_INFORMATION]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_INFORMATION]
(
    [ID]               [bigint]        NOT NULL,
    [NAME]             [nvarchar](127) NOT NULL,
    [ADDRESS]          [nvarchar](255) NOT NULL,
    [PHONE]            [nvarchar](20)  NULL,
    [RATING_AVERAGE]   [float]         NULL,
    [NUMBER_OF_RATING] [smallint]      NULL,
    [VERSION]          [bigint]        NULL,
    CONSTRAINT [PK_PARKING_LOT_INFORMATION] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_RATING]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_RATING]
(
    [ID]             [bigint] IDENTITY (1,1) NOT NULL,
    [CUSTOMER_ID]    [bigint]                NOT NULL,
    [PARKING_LOT_ID] [bigint]                NOT NULL,
    [RATING]         [tinyint]               NOT NULL,
    [COMMENT]        [nvarchar](255)         NULL,
    [LAST_UPDATED]   [smalldatetime]         NULL,
    [VERSION]        [bigint]                NULL,
    CONSTRAINT [PK_PARKING_LOT_RATING] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_SUGGESTION]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_SUGGESTION]
(
    [ID]           [bigint] IDENTITY (1,1) NOT NULL,
    [CUSTOMER_ID]  [bigint]                NOT NULL,
    [NAME]         [nvarchar](127)         NOT NULL,
    [ADDRESS]      [nvarchar](255)         NOT NULL,
    [LATITUDE]     [float]                 NOT NULL,
    [LONGITUDE]    [float]                 NOT NULL,
    [IS_HANDLED]   [bit]                   NULL,
    [LAST_UPDATED] [smalldatetime]         NULL,
    [VERSION]      [bigint]                NULL,
    CONSTRAINT [PK_PARKING_LOT_SUGGESTION] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_TYPE]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_TYPE]
(
    [ID]      [bigint] IDENTITY (1,1) NOT NULL,
    [TYPE]    [nvarchar](50)          NOT NULL,
    [VERSION] [bigint]                NULL,
    CONSTRAINT [PK_PARKING_LOT_TYPE] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_UNIT]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_UNIT]
(
    [ID]                  [bigint] IDENTITY (1,1) NOT NULL,
    [PARKING_LOT_ID]      [bigint]                NOT NULL,
    [LOWER_BOUND_HOUR]    [smallint]              NOT NULL,
    [UPPER_BOUND_HOUR]    [smallint]              NOT NULL,
    [UNIT_PRICE_PER_HOUR] [int]                   NOT NULL,
    [LAST_UPDATED]        [smalldatetime]         NULL,
    [VERSION]             [bigint]                NULL,
    CONSTRAINT [PK_PARKING_LOT_UNIT] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [USER]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [USER]
(
    [ID]           [bigint] IDENTITY (1,1) NOT NULL,
    [ROLE_ID]      [bigint]                NOT NULL,
    [USERNAME]     [nvarchar](30)          NOT NULL,
    [PASSWORD]     [nvarchar](30)          NOT NULL,
    [EMAIL]        [nvarchar](50)          NOT NULL,
    [IS_ACTIVATED] [bit]                   NULL,
    [LAST_SIGN_IN] [smalldatetime]         NULL,
    [VERSION]      [bigint]                NULL,
    CONSTRAINT [PK_USER] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
    CONSTRAINT [CK_USERNAME_UNIQUE] UNIQUE NONCLUSTERED
        (
         [USERNAME] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [USER_ROLE]    Script Date: 3/8/2020 3:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [USER_ROLE]
(
    [ID]      [bigint] IDENTITY (1,1) NOT NULL,
    [ROLE]    [nvarchar](30)          NOT NULL,
    [VERSION] [bigint]                NULL,
    CONSTRAINT [PK_USER_ROLE] PRIMARY KEY CLUSTERED
        (
         [ID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO