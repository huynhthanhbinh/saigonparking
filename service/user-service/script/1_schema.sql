-- noinspection SqlResolveForFile
-- @author: bht

USE [USER]
GO
/****** Object:  Table [CUSTOMER]    Script Date: 4/23/2020 10:34:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [CUSTOMER](
	[ID] [bigint] NOT NULL,
	[FIRST_NAME] [nvarchar](20) NOT NULL,
	[LAST_NAME] [nvarchar](40) NOT NULL,
	[PHONE] [nvarchar](15) NOT NULL,
	[LAST_UPDATED] [smalldatetime] NULL,
 CONSTRAINT [PK_CUSTOMER] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_EMPLOYEE]    Script Date: 4/23/2020 10:34:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_EMPLOYEE](
	[ID] [bigint] NOT NULL,
	[PARKING_LOT_ID] [bigint] NOT NULL,
 CONSTRAINT [PK_PARKING_LOT_EMPLOYEE] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [CK_PARKING_LOT_UNIQUE] UNIQUE NONCLUSTERED 
(
	[PARKING_LOT_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [USER]    Script Date: 4/23/2020 10:34:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [USER](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ROLE_ID] [bigint] NOT NULL,
	[USERNAME] [nvarchar](30) NOT NULL,
	[PASSWORD] [nvarchar](60) NOT NULL,
	[EMAIL] [nvarchar](50) NOT NULL,
	[IS_ACTIVATED] [bit] NULL,
	[LAST_SIGN_IN] [smalldatetime] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_USER] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [CK_USERNAME_UNIQUE] UNIQUE NONCLUSTERED 
(
	[USERNAME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [CK_EMAIL_UNIQUE] UNIQUE NONCLUSTERED
(
	[EMAIL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [USER_ROLE]    Script Date: 4/23/2020 10:34:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [USER_ROLE](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ROLE] [nvarchar](30) NOT NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_USER_ROLE] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO