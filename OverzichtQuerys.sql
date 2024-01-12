/*get a percentage of obtainted certificates for a gender*/
SELECT CASE WHEN EXISTS (SELECT * FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = 'M')) AND EXISTS (SELECT * FROM Certificate WHERE EnrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = 'M'))) THEN ROUND(100 / (SELECT count(*) FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = 'M')) * (SELECT count(*) FROM Certificate WHERE EnrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = 'M'))), 0) ELSE 0 END AS Percentage;

/*get the average progress percentage for all modules in a specific course*/
SELECT ROUND(AVG(p.progressPercentage), 0) AS AverageProgressPercentage, ci.title FROM Progress p INNER JOIN ContentItem ci ON ci.ID = p.contentItemID INNER JOIN Module m ON m.contentItemID = ci.ID WHERE m.courseTitle = 'Painting' GROUP BY ci.title, m.orderNumber ORDER BY m.orderNumber ASC;

/* get the progress from all modules in a specific course */
SELECT p.progressPercentage, ci.title FROM Progress p INNER JOIN ContentItem ci ON ci.ID = p.contentItemID INNER JOIN Module m ON m.contentItemID = ci.ID WHERE m.courseTitle = 'Painting' AND p.userEmail = 'JohnWoodlock@gmail.com' GROUP BY ci.title, m.orderNumber, p.progressPercentage ORDER BY m.orderNumber ASC;

/* get the top 3 most viewed webcasts */
SELECT TOP 3 COUNT(*) AS timesWatched, title, Progress.contentItemID, Webcast.ID AS webcastID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID INNER JOIN Progress ON ContentItem.ID = Progress.contentItemID GROUP BY Progress.contentItemID, title, Webcast.ID ORDER BY COUNT(*) DESC;

/* get the top 3 courses with most enrollments */
SELECT TOP 3 COUNT(*) AS obtainedCertificates, title FROM Course INNER JOIN Enrollment ON Course.title = Enrollment.courseTitle INNER JOIN Certificate ON Enrollment.ID = Certificate.enrollmentID GROUP BY Course.title ORDER BY COUNT(*) DESC;

/* get count of completions for a specific course */
SELECT COUNT(*) AS obtainedCertificates FROM Course INNER JOIN Enrollment ON Course.title = Enrollment.courseTitle INNER JOIN Certificate ON Enrollment.ID = Certificate.enrollmentID WHERE courseTitle = 'WW1';

/* get all certificates for a specific user */
SELECT * FROM Certificate WHERE enrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail = 'AnnaKoppelaar@gmail.com');

/* get all recommended courses for a specific course */
SELECT * FROM RecommendedCourse WHERE originalCourse = 'Painting';