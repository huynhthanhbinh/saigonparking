USE [PARKINGMAP]
GO
/****** Object:  Table [CUSTOMER]    Script Date: 2/19/2020 12:03:27 AM ******/
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
/****** Object:  Table [PARKING_LOT]    Script Date: 2/19/2020 12:03:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PARKING_LOT_TYPE_ID] [tinyint] NOT NULL,
	[LATITUDE] [float] NOT NULL,
	[LONGITUDE] [float] NOT NULL,
	[OPENING_HOUR] [time](7) NOT NULL,
	[CLOSING_HOUR] [time](7) NOT NULL,
	[IS_AVAILABLE] [bit] NULL,
	[LAST_UPDATED] [smalldatetime] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_PARKING_LOT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_EMPLOYEE]    Script Date: 2/19/2020 12:03:27 AM ******/
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
/****** Object:  Table [PARKING_LOT_INFORMATION]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_INFORMATION](
	[ID] [bigint] NOT NULL,
	[NAME] [nvarchar](127) NOT NULL,
	[ADDRESS] [nvarchar](255) NOT NULL,
	[PHONE] [nvarchar](20) NULL,
	[RATING_AVERAGE] [float] NOT NULL,
	[NUMBER_OF_RATING] [smallint] NOT NULL,
	[AVAILABILITY] [smallint] NOT NULL,
	[CAPACITY] [smallint] NOT NULL,
	[PHOTO_PATH] [nvarchar](255) NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_PARKING_LOT_INFORMATION] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_RATING]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_RATING](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[CUSTOMER_ID] [bigint] NOT NULL,
	[PARKING_LOT_ID] [bigint] NOT NULL,
	[RATING] [tinyint] NOT NULL,
	[COMMENT] [nvarchar](255) NULL,
	[LAST_UPDATED] [smalldatetime] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_PARKING_LOT_RATING] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_REPORT]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_REPORT](
	[ID] [bigint] NOT NULL,
	[PARKING_LOT_ID] [bigint] NOT NULL,
	[VIOLATION] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_PARKING_LOT_REPORT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_SUGGESTION]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_SUGGESTION](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[CUSTOMER_ID] [bigint] NOT NULL,
	[NAME] [nvarchar](127) NOT NULL,
	[ADDRESS] [nvarchar](255) NOT NULL,
	[LATITUDE] [float] NOT NULL,
	[LONGITUDE] [float] NOT NULL,
	[IS_HANDLED] [bit] NULL,
	[LAST_UPDATED] [smalldatetime] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_PARKING_LOT_SUGGESTION] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_TYPE]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_TYPE](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[TYPE] [nvarchar](50) NOT NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_PARKING_LOT_TYPE] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [PARKING_LOT_UNIT]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [PARKING_LOT_UNIT](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PARKING_LOT_ID] [bigint] NOT NULL,
	[LOWER_BOUND_HOUR] [smallint] NOT NULL,
	[UPPER_BOUND_HOUR] [smallint] NOT NULL,
	[UNIT_PRICE_PER_HOUR] [int] NOT NULL,
	[LAST_UPDATED] [smalldatetime] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_PARKING_LOT_UNIT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [REPORT]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [REPORT](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[REPORTER_ID] [bigint] NOT NULL,
	[REPORT_TYPE_ID] [tinyint] NOT NULL,
	[IS_HANDLED] [bit] NULL,
	[PHOTO_PATH] [nvarchar](255) NULL,
	[LAST_UPDATED] [smalldatetime] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_REPORT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [REPORT_TYPE]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [REPORT_TYPE](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[TYPE] [nvarchar](255) NOT NULL,
	[DESCRIPTION] [nvarchar](500) NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_REPORT_TYPE] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [USER]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [USER](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[ROLE_ID] [tinyint] NOT NULL,
	[USERNAME] [nvarchar](30) NOT NULL,
	[PASSWORD] [nvarchar](30) NOT NULL,
	[EMAIL] [nvarchar](50) NOT NULL,
	[IS_ACTIVATED] [bit] NULL,
	[LAST_SIGN_IN] [smalldatetime] NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_USER] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [USER_ROLE]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [USER_ROLE](
	[ID] [tinyint] IDENTITY(1,1) NOT NULL,
	[ROLE] [nvarchar](30) NOT NULL,
	[VERSION] [bigint] NULL,
 CONSTRAINT [PK_USER_ROLE] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [WRONG_PARKING_REPORT]    Script Date: 2/19/2020 12:03:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [WRONG_PARKING_REPORT](
	[ID] [bigint] NOT NULL,
	[REGISTRATION_PLATE] [nvarchar](20) NOT NULL,
	[LATITUDE] [float] NOT NULL,
	[LONGITUDE] [float] NOT NULL,
 CONSTRAINT [PK_WRONG_PARKING_REPORT] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [CUSTOMER] ADD  CONSTRAINT [DF_CUSTOMER_LAST_UPDATED]  DEFAULT (getdate()) FOR [LAST_UPDATED]
GO
ALTER TABLE [PARKING_LOT] ADD  CONSTRAINT [DF_PARKING_LOT_IS_AVAILABLE]  DEFAULT ((0)) FOR [IS_AVAILABLE]
GO
ALTER TABLE [PARKING_LOT] ADD  CONSTRAINT [DF_PARKING_LOT_LAST_UPDATED]  DEFAULT (getdate()) FOR [LAST_UPDATED]
GO
ALTER TABLE [PARKING_LOT] ADD  CONSTRAINT [DF_PARKING_LOT_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [PARKING_LOT_INFORMATION] ADD  CONSTRAINT [DF_PARKING_LOT_RATING_AVERAGE]  DEFAULT ((0)) FOR [RATING_AVERAGE]
GO
ALTER TABLE [PARKING_LOT_INFORMATION] ADD  CONSTRAINT [DF_PARKING_LOT_NUMBER_OF_RATING]  DEFAULT ((0)) FOR [NUMBER_OF_RATING]
GO
ALTER TABLE [PARKING_LOT_INFORMATION] ADD  CONSTRAINT [DF_PARKING_LOT_INFORMATION_AVAILABLE_SLOT]  DEFAULT ((0)) FOR [AVAILABILITY]
GO
ALTER TABLE [PARKING_LOT_INFORMATION] ADD  CONSTRAINT [DF_PARKING_LOT_INFORMATION_TOTAL_SLOT]  DEFAULT ((0)) FOR [CAPACITY]
GO
ALTER TABLE [PARKING_LOT_INFORMATION] ADD  CONSTRAINT [DF_PARKING_LOT_INFORMATION_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [PARKING_LOT_RATING] ADD  CONSTRAINT [DF_PARKING_LOT_RATING_LAST_UPDATED]  DEFAULT (getdate()) FOR [LAST_UPDATED]
GO
ALTER TABLE [PARKING_LOT_RATING] ADD  CONSTRAINT [DF_PARKING_LOT_RATING_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [PARKING_LOT_SUGGESTION] ADD  CONSTRAINT [DF_PARKING_LOT_SUGGESTION_IS_HANDLED]  DEFAULT ((0)) FOR [IS_HANDLED]
GO
ALTER TABLE [PARKING_LOT_SUGGESTION] ADD  CONSTRAINT [DF_PARKING_LOT_SUGGESTION_LAST_UPDATED]  DEFAULT (getdate()) FOR [LAST_UPDATED]
GO
ALTER TABLE [PARKING_LOT_SUGGESTION] ADD  CONSTRAINT [DF_PARKING_LOT_SUGGESTION_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [PARKING_LOT_TYPE] ADD  CONSTRAINT [DF_PARKING_LOT_TYPE_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [PARKING_LOT_UNIT] ADD  CONSTRAINT [DF_PARKING_LOT_UNIT_LAST_UPDATED]  DEFAULT (getdate()) FOR [LAST_UPDATED]
GO
ALTER TABLE [PARKING_LOT_UNIT] ADD  CONSTRAINT [DF_PARKING_LOT_UNIT_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [REPORT] ADD  CONSTRAINT [DF_REPORT_IS_HANDLED]  DEFAULT ((0)) FOR [IS_HANDLED]
GO
ALTER TABLE [REPORT] ADD  CONSTRAINT [DF_REPORT_LAST_UPDATED]  DEFAULT (getdate()) FOR [LAST_UPDATED]
GO
ALTER TABLE [REPORT] ADD  CONSTRAINT [DF_REPORT_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [REPORT_TYPE] ADD  CONSTRAINT [DF_REPORT_TYPE_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [USER] ADD  CONSTRAINT [DF_USER_IS_ACTIVATED]  DEFAULT ((0)) FOR [IS_ACTIVATED]
GO
ALTER TABLE [USER] ADD  CONSTRAINT [DF_USER_LAST_SIGN_IN]  DEFAULT (getdate()) FOR [LAST_SIGN_IN]
GO
ALTER TABLE [USER] ADD  CONSTRAINT [DF_USER_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [USER_ROLE] ADD  CONSTRAINT [DF_USER_ROLE_VERSION]  DEFAULT ((0)) FOR [VERSION]
GO
ALTER TABLE [CUSTOMER]  WITH CHECK ADD  CONSTRAINT [FK_CUSTOMER_USER] FOREIGN KEY([ID])
REFERENCES [USER] ([ID])
GO
ALTER TABLE [CUSTOMER] CHECK CONSTRAINT [FK_CUSTOMER_USER]
GO
ALTER TABLE [PARKING_LOT]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_PARKING_LOT_TYPE] FOREIGN KEY([PARKING_LOT_TYPE_ID])
REFERENCES [PARKING_LOT_TYPE] ([ID])
GO
ALTER TABLE [PARKING_LOT] CHECK CONSTRAINT [FK_PARKING_LOT_PARKING_LOT_TYPE]
GO
ALTER TABLE [PARKING_LOT_EMPLOYEE]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_EMPLOYEE_PARKING_LOT] FOREIGN KEY([PARKING_LOT_ID])
REFERENCES [PARKING_LOT] ([ID])
GO
ALTER TABLE [PARKING_LOT_EMPLOYEE] CHECK CONSTRAINT [FK_PARKING_LOT_EMPLOYEE_PARKING_LOT]
GO
ALTER TABLE [PARKING_LOT_EMPLOYEE]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_EMPLOYEE_USER] FOREIGN KEY([ID])
REFERENCES [USER] ([ID])
GO
ALTER TABLE [PARKING_LOT_EMPLOYEE] CHECK CONSTRAINT [FK_PARKING_LOT_EMPLOYEE_USER]
GO
ALTER TABLE [PARKING_LOT_INFORMATION]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_INFORMATION_PARKING_LOT] FOREIGN KEY([ID])
REFERENCES [PARKING_LOT] ([ID])
GO
ALTER TABLE [PARKING_LOT_INFORMATION] CHECK CONSTRAINT [FK_PARKING_LOT_INFORMATION_PARKING_LOT]
GO
ALTER TABLE [PARKING_LOT_RATING]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_RATING_CUSTOMER] FOREIGN KEY([CUSTOMER_ID])
REFERENCES [CUSTOMER] ([ID])
GO
ALTER TABLE [PARKING_LOT_RATING] CHECK CONSTRAINT [FK_PARKING_LOT_RATING_CUSTOMER]
GO
ALTER TABLE [PARKING_LOT_RATING]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_RATING_PARKING_LOT] FOREIGN KEY([PARKING_LOT_ID])
REFERENCES [PARKING_LOT] ([ID])
GO
ALTER TABLE [PARKING_LOT_RATING] CHECK CONSTRAINT [FK_PARKING_LOT_RATING_PARKING_LOT]
GO
ALTER TABLE [PARKING_LOT_REPORT]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_REPORT_PARKING_LOT] FOREIGN KEY([PARKING_LOT_ID])
REFERENCES [PARKING_LOT] ([ID])
GO
ALTER TABLE [PARKING_LOT_REPORT] CHECK CONSTRAINT [FK_PARKING_LOT_REPORT_PARKING_LOT]
GO
ALTER TABLE [PARKING_LOT_REPORT]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_REPORT_REPORT] FOREIGN KEY([ID])
REFERENCES [REPORT] ([ID])
GO
ALTER TABLE [PARKING_LOT_REPORT] CHECK CONSTRAINT [FK_PARKING_LOT_REPORT_REPORT]
GO
ALTER TABLE [PARKING_LOT_SUGGESTION]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_SUGGESTION_CUSTOMER] FOREIGN KEY([CUSTOMER_ID])
REFERENCES [CUSTOMER] ([ID])
GO
ALTER TABLE [PARKING_LOT_SUGGESTION] CHECK CONSTRAINT [FK_PARKING_LOT_SUGGESTION_CUSTOMER]
GO
ALTER TABLE [PARKING_LOT_UNIT]  WITH CHECK ADD  CONSTRAINT [FK_PARKING_LOT_UNIT_PARKING_LOT] FOREIGN KEY([PARKING_LOT_ID])
REFERENCES [PARKING_LOT] ([ID])
GO
ALTER TABLE [PARKING_LOT_UNIT] CHECK CONSTRAINT [FK_PARKING_LOT_UNIT_PARKING_LOT]
GO
ALTER TABLE [REPORT]  WITH CHECK ADD  CONSTRAINT [FK_REPORT_CUSTOMER] FOREIGN KEY([REPORTER_ID])
REFERENCES [CUSTOMER] ([ID])
GO
ALTER TABLE [REPORT] CHECK CONSTRAINT [FK_REPORT_CUSTOMER]
GO
ALTER TABLE [REPORT]  WITH CHECK ADD  CONSTRAINT [FK_REPORT_REPORT_TYPE] FOREIGN KEY([REPORT_TYPE_ID])
REFERENCES [REPORT_TYPE] ([ID])
GO
ALTER TABLE [REPORT] CHECK CONSTRAINT [FK_REPORT_REPORT_TYPE]
GO
ALTER TABLE [USER]  WITH CHECK ADD  CONSTRAINT [FK_USER_USER_ROLE] FOREIGN KEY([ROLE_ID])
REFERENCES [USER_ROLE] ([ID])
GO
ALTER TABLE [USER] CHECK CONSTRAINT [FK_USER_USER_ROLE]
GO
ALTER TABLE [WRONG_PARKING_REPORT]  WITH CHECK ADD  CONSTRAINT [FK_WRONG_PARKING_REPORT_REPORT] FOREIGN KEY([ID])
REFERENCES [REPORT] ([ID])
GO
ALTER TABLE [WRONG_PARKING_REPORT] CHECK CONSTRAINT [FK_WRONG_PARKING_REPORT_REPORT]
GO
ALTER TABLE [PARKING_LOT_INFORMATION]  WITH CHECK ADD  CONSTRAINT [CK_PARKING_LOT_INFORMATION] CHECK  (([AVAILABILITY]>=(0) AND [CAPACITY]>=(0) AND [CAPACITY]>=[AVAILABILITY]))
GO
ALTER TABLE [PARKING_LOT_INFORMATION] CHECK CONSTRAINT [CK_PARKING_LOT_INFORMATION]
GO
ALTER TABLE [PARKING_LOT_RATING]  WITH CHECK ADD  CONSTRAINT [CK_PARKING_LOT_RATING] CHECK  (([RATING]=(5) OR [RATING]=(4) OR [RATING]=(3) OR [RATING]=(2) OR [RATING]=(1)))
GO
ALTER TABLE [PARKING_LOT_RATING] CHECK CONSTRAINT [CK_PARKING_LOT_RATING]
GO