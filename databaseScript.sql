USE [master]
GO
/****** Object:  Database [Codecadamy]    Script Date: 12/7/2023 4:45:54 PM ******/
CREATE DATABASE [Codecadamy]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Codecadamy', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\Codecadamy.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Codecadamy_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\Codecadamy_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Codecadamy] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Codecadamy].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Codecadamy] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Codecadamy] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Codecadamy] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Codecadamy] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Codecadamy] SET ARITHABORT OFF 
GO
ALTER DATABASE [Codecadamy] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Codecadamy] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Codecadamy] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Codecadamy] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Codecadamy] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Codecadamy] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Codecadamy] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Codecadamy] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Codecadamy] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Codecadamy] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Codecadamy] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Codecadamy] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Codecadamy] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Codecadamy] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Codecadamy] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Codecadamy] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Codecadamy] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Codecadamy] SET RECOVERY FULL 
GO
ALTER DATABASE [Codecadamy] SET  MULTI_USER 
GO
ALTER DATABASE [Codecadamy] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Codecadamy] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Codecadamy] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Codecadamy] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Codecadamy] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Codecadamy] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Codecadamy', N'ON'
GO
ALTER DATABASE [Codecadamy] SET QUERY_STORE = ON
GO
ALTER DATABASE [Codecadamy] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Codecadamy]
GO
/****** Object:  User [SQLDataBaseRemoteLogin]    Script Date: 12/7/2023 4:45:54 PM ******/
CREATE USER [SQLDataBaseRemoteLogin] FOR LOGIN [SQLDataBaseRemoteLogin] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 12/7/2023 4:45:54 PM ******/
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
/****** Object:  Table [dbo].[Content Item]    Script Date: 12/7/2023 4:45:54 PM ******/
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
/****** Object:  Table [dbo].[Course]    Script Date: 12/7/2023 4:45:54 PM ******/
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
/****** Object:  Table [dbo].[Enrolment]    Script Date: 12/7/2023 4:45:54 PM ******/
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
/****** Object:  Table [dbo].[Module]    Script Date: 12/7/2023 4:45:54 PM ******/
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
/****** Object:  Table [dbo].[progressPercetage]    Script Date: 12/7/2023 4:45:54 PM ******/
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
/****** Object:  Table [dbo].[User]    Script Date: 12/7/2023 4:45:54 PM ******/
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
/****** Object:  Table [dbo].[Webcast]    Script Date: 12/7/2023 4:45:54 PM ******/
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
USE [master]
GO
ALTER DATABASE [Codecadamy] SET  READ_WRITE 
GO
