USE [Codecademy]
GO
/****** Object:  User [group1]    Script Date: 12-1-2024 22:02:07 ******/
CREATE USER [group1] FOR LOGIN [group1] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_datareader] ADD MEMBER [group1]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [group1]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[rating] [int] NOT NULL,
	[employeeName] [nvarchar](50) NOT NULL,
	[enrollmentID] [int] NOT NULL,
 CONSTRAINT [PK_Certificate] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContactPerson]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContactPerson](
	[email] [nvarchar](50) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_ContactPerson] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContentItem]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContentItem](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](50) NOT NULL,
	[publicationDate] [date] NOT NULL,
	[status] [nvarchar](50) NOT NULL,
	[description] [text] NOT NULL,
 CONSTRAINT [PK_ContentItem] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[title] [nvarchar](50) NOT NULL,
	[subject] [nvarchar](50) NOT NULL,
	[introText] [text] NOT NULL,
	[difficultyLevel] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Course] PRIMARY KEY CLUSTERED 
(
	[title] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[enrollmentDate] [date] NOT NULL,
	[userEmail] [nvarchar](50) NOT NULL,
	[courseTitle] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Enrollment] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[version] [float] NOT NULL,
	[orderNumber] [int] NULL,
	[emailContactPerson] [nvarchar](50) NOT NULL,
	[contentItemID] [int] NOT NULL,
	[courseTitle] [nvarchar](50) NULL,
 CONSTRAINT [PK_Module] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Progress]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Progress](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[progressPercentage] [int] NOT NULL,
	[userEmail] [nvarchar](50) NOT NULL,
	[contentItemID] [int] NOT NULL,
 CONSTRAINT [PK_ProgressPercentage] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RecommendedCourse]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RecommendedCourse](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[originalCourse] [nvarchar](50) NOT NULL,
	[recommendedCourse] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_RecommendedCourse] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Speaker]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Speaker](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[organization] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Speaker] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[email] [nvarchar](50) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[dateOfBirth] [date] NOT NULL,
	[gender] [nvarchar](1) NOT NULL,
	[address] [nvarchar](50) NOT NULL,
	[postalCode] [nchar](50) NOT NULL,
	[residence] [nvarchar](50) NOT NULL,
	[country] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 12-1-2024 22:02:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[URL] [nvarchar](50) NOT NULL,
	[speakerID] [int] NOT NULL,
	[contentItemID] [int] NOT NULL,
 CONSTRAINT [PK_Webcast] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Certificate] ON 

INSERT [dbo].[Certificate] ([ID], [rating], [employeeName], [enrollmentID]) VALUES (1, 5, N'Charlie', 1)
INSERT [dbo].[Certificate] ([ID], [rating], [employeeName], [enrollmentID]) VALUES (2, 3, N'Charlie', 2)
INSERT [dbo].[Certificate] ([ID], [rating], [employeeName], [enrollmentID]) VALUES (3, 9, N'Charlie', 5)
INSERT [dbo].[Certificate] ([ID], [rating], [employeeName], [enrollmentID]) VALUES (4, 8, N'Bob', 9)
INSERT [dbo].[Certificate] ([ID], [rating], [employeeName], [enrollmentID]) VALUES (5, 7, N'David', 7)
INSERT [dbo].[Certificate] ([ID], [rating], [employeeName], [enrollmentID]) VALUES (6, 6, N'Charlie', 10)
SET IDENTITY_INSERT [dbo].[Certificate] OFF
GO
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'Bastiaan23@gmail.com', N'Bastiaan van Lengen')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'HaraldHardrade445@gmail.com', N'Harald Hardrade')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'Henriette356@outlook.com', N'Henriette van Beesten')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'IgorLewandoski32@gmail.com', N'Igor Lewandoski')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'JaimyVanGouden44@gmail.com', N'Jaimy van Gouden')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'JenteVanDijek@gmail.com', N'Jente van Dijek')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'Mees894@outlook.com', N'Mees Lint')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'RomanovPeter44@gmail.com', N'Peter Romanov')
INSERT [dbo].[ContactPerson] ([email], [name]) VALUES (N'Willem67Lamp@gmail.com', N'Willem Lamp')
GO
SET IDENTITY_INSERT [dbo].[ContentItem] ON 

INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (1, N'Blending', CAST(N'2022-04-14' AS Date), N'ACTIVE', N'In this module we will learn how to correctly blend colours, so you can create the perfect colour. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (2, N'Lighting', CAST(N'2017-04-28' AS Date), N'ARCHIVED', N'In this course we will learn how to create great lighting, so that all your paintings look very realistic. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (3, N'Depth', CAST(N'2021-03-30' AS Date), N'CONCEPT', N'In this module we will learn how to create depth and work with perspective, this will bring your painting to life. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (4, N'Before the war', CAST(N'2024-01-17' AS Date), N'ARCHIVED', N'In this module we learn about the factors that lead to the outbreak of the war.')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (5, N'The trenches', CAST(N'2017-04-21' AS Date), N'ACTIVE', N'In this module we learn about the soldiers who fought in the trenches. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (6, N'The treaty of Versailles', CAST(N'2023-04-15' AS Date), N'CONCEPT', N'In this module we learn about the peacedeal and how it affected the winning and losing sides. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (7, N'Setting up HashMaps', CAST(N'2021-05-14' AS Date), N'ACTIVE', N'In this module we learn how to setup your pc for coding and how to start using HashMaps.')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (8, N'Using HashMaps', CAST(N'2023-01-27' AS Date), N'CONCEPT', N'In this module we use the HashMaps more complicated. 	')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (9, N'Implementing the HashMap', CAST(N'2021-06-20' AS Date), N'ACTIVE', N'In this module we will implement the HashMap in a project and make it working. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (10, N'Old English Literacy', CAST(N'2023-06-11' AS Date), N'ACTIVE', N'In this module we learn about the old english literacy, like the vikings or the normans. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (11, N'William Shakespear', CAST(N'2015-01-25' AS Date), N'ARCHIVED', N'We learn about the famous writer William Shakespear in this course. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (12, N'Modern Literacy', CAST(N'2022-01-13' AS Date), N'ACTIVE', N'We will learn about the modern literacy in this course, by looking at newly published books and articles. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (13, N'Business Setup', CAST(N'2023-12-09' AS Date), N'ACTIVE', N'We will learn the initial stages of setting up your business and taking out loans.')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (14, N'Growing your Business', CAST(N'2021-01-23' AS Date), N'CONCEPT', N'In this module we learn how to enable growth in your business. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (15, N'What to invest in', CAST(N'2019-01-25' AS Date), N'ACTIVE', N'After you are making decent money, what should you invest in, in this course you will learn all about that. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (16, N'Signing up for exams', CAST(N'2018-02-02' AS Date), N'ACTIVE', N'In this webcast we will teach you how to sign yourself up for an exam	')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (17, N'How to prepare for an exam', CAST(N'2024-01-23' AS Date), N'ARCHIVED', N'In this webcast we will teach you how to organize and set yourself up to achieve good grades. 	')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (18, N'Choices after school', CAST(N'2021-12-24' AS Date), N'ARCHIVED', N'In this webcast we will teach you about the possiblities after school')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (19, N'How to work in groups', CAST(N'2017-01-18' AS Date), N'ACTIVE', N'In this webcast we will teach you how to work in groups with other people and what to expect. ')
INSERT [dbo].[ContentItem] ([ID], [title], [publicationDate], [status], [description]) VALUES (20, N'Documentary about the school', CAST(N'2021-02-04' AS Date), N'ARCHIVED', N'In this webcast we will teach you about the history of the school and the founder')
SET IDENTITY_INSERT [dbo].[ContentItem] OFF
GO
INSERT [dbo].[Course] ([title], [subject], [introText], [difficultyLevel]) VALUES (N'Business Management', N'Economics', N'In this course you will learn about how to setup and manage a business. ', N'BEGINNER')
INSERT [dbo].[Course] ([title], [subject], [introText], [difficultyLevel]) VALUES (N'English Literacy', N'English', N'In this course you will learn about all the great works created by famous English writers', N'ADVANCED')
INSERT [dbo].[Course] ([title], [subject], [introText], [difficultyLevel]) VALUES (N'HashMaps', N'Java', N'This is a course to learn how to use the HashMap feature in Java.', N'BEGINNER')
INSERT [dbo].[Course] ([title], [subject], [introText], [difficultyLevel]) VALUES (N'Painting', N'Arts', N'In this course you will learn all about painting and using different paint methods. ', N'EXPERT')
INSERT [dbo].[Course] ([title], [subject], [introText], [difficultyLevel]) VALUES (N'WW1', N'History', N'In this course we dive into what the First World War was. ', N'ADVANCED')
GO
SET IDENTITY_INSERT [dbo].[Enrollment] ON 

INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (1, CAST(N'2024-01-12' AS Date), N'AnnaKoppelaar@gmail.com', N'Business Management')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (2, CAST(N'2024-01-12' AS Date), N'Boaz173@hotmail.com', N'English Literacy')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (3, CAST(N'2024-01-12' AS Date), N'FabianoNacho23@gmail.com', N'WW1')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (4, CAST(N'2024-01-12' AS Date), N'JohnWoodlock@gmail.com', N'Painting')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (5, CAST(N'2024-01-12' AS Date), N'Joseph_Freeman@gmail.com', N'English Literacy')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (6, CAST(N'2024-01-12' AS Date), N'Joshua_Lange@outlook.com', N'HashMaps')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (7, CAST(N'2024-01-12' AS Date), N'Lisanne732@hotmail.com', N'WW1')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (8, CAST(N'2024-01-12' AS Date), N'Markie45@gmail.com', N'HashMaps')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (9, CAST(N'2024-01-12' AS Date), N'Renske.Groothard@outlook.com', N'English Literacy')
INSERT [dbo].[Enrollment] ([ID], [enrollmentDate], [userEmail], [courseTitle]) VALUES (10, CAST(N'2024-01-12' AS Date), N'Tom_de_Bom@gmail.com', N'WW1')
SET IDENTITY_INSERT [dbo].[Enrollment] OFF
GO
SET IDENTITY_INSERT [dbo].[Module] ON 

INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (1, 1.2, 3, N'Henriette356@outlook.com', 1, N'Painting')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (2, 3.1, 2, N'JaimyVanGouden44@gmail.com', 2, N'Painting')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (3, 2.5, 1, N'Willem67Lamp@gmail.com', 3, N'Painting')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (4, 3.4, 1, N'IgorLewandoski32@gmail.com', 4, N'WW1')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (5, 9.3, 2, N'JenteVanDijek@gmail.com', 5, N'WW1')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (6, 3.6, 3, N'Mees894@outlook.com', 6, N'WW1')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (7, 21.3, 1, N'Mees894@outlook.com', 7, N'HashMaps')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (8, 3.5, 3, N'JaimyVanGouden44@gmail.com', 8, N'HashMaps')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (9, 2.7, 2, N'IgorLewandoski32@gmail.com', 9, N'HashMaps')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (10, 3.8, 1, N'Henriette356@outlook.com', 10, N'English Literacy')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (11, 12.8, 3, N'Willem67Lamp@gmail.com', 11, N'English Literacy')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (12, 1.2, 2, N'JaimyVanGouden44@gmail.com', 12, N'English Literacy')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (13, 1.1, 1, N'HaraldHardrade445@gmail.com', 13, N'Business Management')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (14, 4.4, 3, N'Bastiaan23@gmail.com', 14, N'Business Management')
INSERT [dbo].[Module] ([ID], [version], [orderNumber], [emailContactPerson], [contentItemID], [courseTitle]) VALUES (15, 9.6, 2, N'RomanovPeter44@gmail.com', 15, N'Business Management')
SET IDENTITY_INSERT [dbo].[Module] OFF
GO
SET IDENTITY_INSERT [dbo].[Progress] ON 

INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (1, 90, N'AnnaKoppelaar@gmail.com', 13)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (2, 96, N'AnnaKoppelaar@gmail.com', 15)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (3, 63, N'AnnaKoppelaar@gmail.com', 14)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (4, 90, N'Boaz173@hotmail.com', 10)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (5, 53, N'Boaz173@hotmail.com', 12)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (6, 10, N'Boaz173@hotmail.com', 11)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (7, 12, N'FabianoNacho23@gmail.com', 4)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (8, 64, N'FabianoNacho23@gmail.com', 5)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (9, 27, N'FabianoNacho23@gmail.com', 6)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (10, 91, N'JohnWoodlock@gmail.com', 3)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (11, 64, N'JohnWoodlock@gmail.com', 2)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (12, 7, N'JohnWoodlock@gmail.com', 1)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (13, 51, N'Joseph_Freeman@gmail.com', 10)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (14, 6, N'Joseph_Freeman@gmail.com', 12)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (15, 37, N'Joseph_Freeman@gmail.com', 11)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (16, 19, N'Joshua_Lange@outlook.com', 7)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (17, 94, N'Joshua_Lange@outlook.com', 9)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (18, 2, N'Joshua_Lange@outlook.com', 8)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (19, 8, N'Lisanne732@hotmail.com', 4)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (20, 39, N'Lisanne732@hotmail.com', 5)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (21, 53, N'Lisanne732@hotmail.com', 6)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (22, 36, N'Markie45@gmail.com', 7)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (23, 3, N'Markie45@gmail.com', 9)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (24, 10, N'Markie45@gmail.com', 8)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (25, 79, N'Renske.Groothard@outlook.com', 10)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (26, 34, N'Renske.Groothard@outlook.com', 12)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (27, 17, N'Renske.Groothard@outlook.com', 11)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (28, 37, N'Tom_de_Bom@gmail.com', 4)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (29, 33, N'Tom_de_Bom@gmail.com', 5)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (30, 25, N'Tom_de_Bom@gmail.com', 6)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (31, 0, N'AnnaKoppelaar@gmail.com', 16)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (32, 0, N'Boaz173@hotmail.com', 17)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (33, 0, N'FabianoNacho23@gmail.com', 18)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (34, 22, N'Joseph_Freeman@gmail.com', 20)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (35, 83, N'JohnWoodlock@gmail.com', 18)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (36, 58, N'Joshua_Lange@outlook.com', 20)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (37, 64, N'Lisanne732@hotmail.com', 17)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (38, 68, N'Markie45@gmail.com', 19)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (39, 12, N'Renske.Groothard@outlook.com', 16)
INSERT [dbo].[Progress] ([ID], [progressPercentage], [userEmail], [contentItemID]) VALUES (40, 29, N'Tom_de_Bom@gmail.com', 17)
SET IDENTITY_INSERT [dbo].[Progress] OFF
GO
SET IDENTITY_INSERT [dbo].[RecommendedCourse] ON 

INSERT [dbo].[RecommendedCourse] ([ID], [originalCourse], [recommendedCourse]) VALUES (1, N'English Literacy', N'Painting')
INSERT [dbo].[RecommendedCourse] ([ID], [originalCourse], [recommendedCourse]) VALUES (2, N'HashMaps', N'English Literacy')
INSERT [dbo].[RecommendedCourse] ([ID], [originalCourse], [recommendedCourse]) VALUES (3, N'Painting', N'WW1')
SET IDENTITY_INSERT [dbo].[RecommendedCourse] OFF
GO
SET IDENTITY_INSERT [dbo].[Speaker] ON 

INSERT [dbo].[Speaker] ([ID], [name], [organization]) VALUES (1, N'Henry van de Hoeve', N'Avans Hogeschool')
INSERT [dbo].[Speaker] ([ID], [name], [organization]) VALUES (2, N'Marieke de Korte', N'Hogeschool Rotterdam')
INSERT [dbo].[Speaker] ([ID], [name], [organization]) VALUES (3, N'Lizanne Dabrowska', N'Hogeschool Utrecht')
INSERT [dbo].[Speaker] ([ID], [name], [organization]) VALUES (4, N'Harry the Swift', N'Oxford')
INSERT [dbo].[Speaker] ([ID], [name], [organization]) VALUES (5, N'Oswald Dresden', N'Oxford')
INSERT [dbo].[Speaker] ([ID], [name], [organization]) VALUES (6, N'Fenri Ivanovich', N'Oxford')
SET IDENTITY_INSERT [dbo].[Speaker] OFF
GO
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'AnnaKoppelaar@gmail.com', N'Anna Koppelaar', CAST(N'1954-07-27' AS Date), N'F', N'Hoevenlaan 12', N'3823 UI                                           ', N'Den Haag', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'Boaz173@hotmail.com', N'Boaz van Dijk', CAST(N'2006-01-11' AS Date), N'M', N'Kralingen Straat 83', N'1243 YL                                           ', N'Alblasserdam', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'FabianoNacho23@gmail.com', N'Fabiano Nacho', CAST(N'1998-03-24' AS Date), N'M', N'Lindenlaan 21', N'3823 MV                                           ', N'Breda', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'JohnWoodlock@gmail.com', N'John Woodlock', CAST(N'1998-01-15' AS Date), N'M', N'Beethovenstaat 21', N'3363 KG                                           ', N'Sliedrecht', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'Joseph_Freeman@gmail.com', N'Joseph Freeman', CAST(N'1983-12-25' AS Date), N'M', N'Oranjewit Straat 103', N'3823 KH                                           ', N'Rotterdam', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'Joshua_Lange@outlook.com', N'Joshua de Lange', CAST(N'1993-02-17' AS Date), N'M', N'Hendrick Laan 92', N'7932 BM                                           ', N'Utrecht', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'Lisanne732@hotmail.com', N'Lisanne van Veen', CAST(N'2002-10-21' AS Date), N'F', N'Willem Straat 39', N'7232 KN                                           ', N'Amsterdam', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'Markie45@gmail.com', N'Mark de Korte', CAST(N'2002-06-29' AS Date), N'M', N'Valkenlaan 93', N'7293 AS                                           ', N'Dordrecht', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'Renske.Groothard@outlook.com', N'Renske Groothard', CAST(N'1971-03-23' AS Date), N'F', N'Rondo Straat 94', N'7932 LM                                           ', N'Tilburg', N'The Netherlands')
INSERT [dbo].[User] ([email], [name], [dateOfBirth], [gender], [address], [postalCode], [residence], [country]) VALUES (N'Tom_de_Bom@gmail.com', N'Tom de Groot', CAST(N'2009-06-10' AS Date), N'M', N'Harry Bannik Straat 7', N'1105 AW                                           ', N'Anhem', N'The Netherlands')
GO
SET IDENTITY_INSERT [dbo].[Webcast] ON 

INSERT [dbo].[Webcast] ([ID], [URL], [speakerID], [contentItemID]) VALUES (1, N'https://video.signup.com', 3, 16)
INSERT [dbo].[Webcast] ([ID], [URL], [speakerID], [contentItemID]) VALUES (2, N'http://youtube.prepareexam.com', 1, 17)
INSERT [dbo].[Webcast] ([ID], [URL], [speakerID], [contentItemID]) VALUES (3, N'https://www.afterschool.com', 4, 18)
INSERT [dbo].[Webcast] ([ID], [URL], [speakerID], [contentItemID]) VALUES (4, N'https://workingingroups.foryou.nl', 5, 19)
INSERT [dbo].[Webcast] ([ID], [URL], [speakerID], [contentItemID]) VALUES (5, N'https://history.ofmyschool.com', 6, 20)
SET IDENTITY_INSERT [dbo].[Webcast] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [userEmail_contentItem_unique]    Script Date: 12-1-2024 22:02:07 ******/
ALTER TABLE [dbo].[Progress] ADD  CONSTRAINT [userEmail_contentItem_unique] UNIQUE NONCLUSTERED 
(
	[contentItemID] ASC,
	[userEmail] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Certificate]  WITH CHECK ADD  CONSTRAINT [FK_Certificate_Enrollment] FOREIGN KEY([enrollmentID])
REFERENCES [dbo].[Enrollment] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Certificate] CHECK CONSTRAINT [FK_Certificate_Enrollment]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_Course] FOREIGN KEY([courseTitle])
REFERENCES [dbo].[Course] ([title])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_Course]
GO
ALTER TABLE [dbo].[Enrollment]  WITH CHECK ADD  CONSTRAINT [FK_Enrollment_User] FOREIGN KEY([userEmail])
REFERENCES [dbo].[User] ([email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Enrollment] CHECK CONSTRAINT [FK_Enrollment_User]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_ContactPerson] FOREIGN KEY([emailContactPerson])
REFERENCES [dbo].[ContactPerson] ([email])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_ContactPerson]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_ContentItem] FOREIGN KEY([contentItemID])
REFERENCES [dbo].[ContentItem] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_ContentItem]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD  CONSTRAINT [FK_Module_Course] FOREIGN KEY([courseTitle])
REFERENCES [dbo].[Course] ([title])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Module] CHECK CONSTRAINT [FK_Module_Course]
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD  CONSTRAINT [FK_ProgressPercentage_ContentItem] FOREIGN KEY([contentItemID])
REFERENCES [dbo].[ContentItem] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Progress] CHECK CONSTRAINT [FK_ProgressPercentage_ContentItem]
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD  CONSTRAINT [FK_ProgressPercentage_User] FOREIGN KEY([userEmail])
REFERENCES [dbo].[User] ([email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Progress] CHECK CONSTRAINT [FK_ProgressPercentage_User]
GO
ALTER TABLE [dbo].[RecommendedCourse]  WITH CHECK ADD  CONSTRAINT [FK_RecommendedCourse_Course] FOREIGN KEY([originalCourse])
REFERENCES [dbo].[Course] ([title])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[RecommendedCourse] CHECK CONSTRAINT [FK_RecommendedCourse_Course]
GO
ALTER TABLE [dbo].[RecommendedCourse]  WITH CHECK ADD  CONSTRAINT [FK_RecommendedCourse_Course1] FOREIGN KEY([recommendedCourse])
REFERENCES [dbo].[Course] ([title])
GO
ALTER TABLE [dbo].[RecommendedCourse] CHECK CONSTRAINT [FK_RecommendedCourse_Course1]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_ContentItem] FOREIGN KEY([contentItemID])
REFERENCES [dbo].[ContentItem] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_ContentItem]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD  CONSTRAINT [FK_Webcast_Speaker] FOREIGN KEY([speakerID])
REFERENCES [dbo].[Speaker] ([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Webcast] CHECK CONSTRAINT [FK_Webcast_Speaker]
GO
