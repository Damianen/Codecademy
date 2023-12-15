USE [Codecadamy]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[ID] [int] NOT NULL,
	[rating] [int] NULL,
	[employeeName] [varchar](50) NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Content Item]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Content Item](
	[ID] [int] NOT NULL,
	[publicationDate] [date] NULL,
	[Status] [varchar](50) NULL,
	[Description] [varchar](max) NULL,
	[TrackingNumber] [int] NULL,
 CONSTRAINT [PK_Content Item] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[name] [varchar](50) NOT NULL,
	[subject] [varbinary](50) NULL,
	[introText] [varchar](max) NULL,
	[level] [int] NULL,
 CONSTRAINT [PK_Course] PRIMARY KEY CLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrolment]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrolment](
	[userEmail] [varchar](100) NOT NULL,
	[courseName] [varchar](50) NOT NULL,
	[enrolmentDate] [date] NOT NULL,
	[certificateID] [int] NULL,
 CONSTRAINT [PK_Enrolment] PRIMARY KEY CLUSTERED 
(
	[userEmail] ASC,
	[courseName] ASC,
	[enrolmentDate] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[title] [varchar](50) NOT NULL,
	[version] [varchar](50) NULL,
	[nameContactPerson] [varchar](50) NULL,
	[emailContactPerson] [varchar](100) NULL,
	[contentItemID] [int] NULL,
	[courseName] [varchar](50) NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[progressPercetage]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[progressPercetage](
	[progress] [decimal](18, 0) NULL,
	[userEmail] [varchar](100) NULL,
	[contentItemID] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[email] [varchar](100) NOT NULL,
	[name] [varchar](50) NULL,
	[dateOfBirth] [date] NULL,
	[gender] [char](1) NULL,
	[adress] [varchar](50) NULL,
	[residence] [varchar](50) NULL,
	[country] [varchar](50) NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 12/15/2023 2:12:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[title] [varchar](50) NOT NULL,
	[URL] [varchar](max) NULL,
	[speaker] [varchar](50) NULL,
	[orginization] [varchar](50) NULL,
	[contentItemID] [int] NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Enrolment]  WITH CHECK ADD  CONSTRAINT [FK_Enrolment_Certificate] FOREIGN KEY([certificateID])
REFERENCES [dbo].[Certificate] ([ID])
GO
ALTER TABLE [dbo].[Enrolment] CHECK CONSTRAINT [FK_Enrolment_Certificate]
GO
ALTER TABLE [dbo].[Enrolment]  WITH CHECK ADD  CONSTRAINT [FK_Enrolment_Course] FOREIGN KEY([courseName])
REFERENCES [dbo].[Course] ([name])
GO
ALTER TABLE [dbo].[Enrolment] CHECK CONSTRAINT [FK_Enrolment_Course]
GO
ALTER TABLE [dbo].[Enrolment]  WITH CHECK ADD  CONSTRAINT [FK_Enrolment_User] FOREIGN KEY([userEmail])
REFERENCES [dbo].[User] ([email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrolment] CHECK CONSTRAINT [FK_Enrolment_User]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Content Item] FOREIGN KEY([contentItemID])
REFERENCES [dbo].[Content Item] ([ID])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Content Item]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Course] FOREIGN KEY([courseName])
REFERENCES [dbo].[Course] ([name])
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Course]
GO
ALTER TABLE [dbo].[progressPercetage]  WITH CHECK ADD  CONSTRAINT [FK_progressPercetage_Content Item] FOREIGN KEY([contentItemID])
REFERENCES [dbo].[Content Item] ([ID])
GO
ALTER TABLE [dbo].[progressPercetage] CHECK CONSTRAINT [FK_progressPercetage_Content Item]
GO
ALTER TABLE [dbo].[progressPercetage]  WITH CHECK ADD  CONSTRAINT [FK_progressPercetage_User] FOREIGN KEY([userEmail])
REFERENCES [dbo].[User] ([email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[progressPercetage] CHECK CONSTRAINT [FK_progressPercetage_User]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_Content Item] FOREIGN KEY([contentItemID])
REFERENCES [dbo].[Content Item] ([ID])
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_Content Item]
GO
