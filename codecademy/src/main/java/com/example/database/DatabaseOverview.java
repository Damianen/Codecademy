package com.example.database;

import com.example.course.Course;
import com.example.course.Webcast;
import com.example.user.Certificate;
import com.example.user.User;
import com.example.user.User.Gender;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseOverview extends Database {

    // get count of obtainted certificates for gender
    public static int getObtaintedCertificatesPercentageForGender(Gender gender) {

        // set up variables
        String SQL = "SELECT CASE WHEN EXISTS (SELECT * FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = '"
                + gender
                + "')) AND EXISTS (SELECT * FROM Certificate WHERE EnrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = '"
                + gender
                + "'))) THEN ROUND(100 / (SELECT count(*) FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = '"
                + gender
                + "')) * (SELECT count(*) FROM Certificate WHERE EnrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = '"
                + gender + "'))), 0) ELSE 0 END AS Percentage";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        int data = 0;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int percentage = rs.getInt("Percentage");

                data = percentage;
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }

    // get the average progress percentage for all modules in a specific course
    public static HashMap<String, Integer> getAveragePogressPercentagePerCourseModule(Course course) {

        // set up variables
        String SQL = "SELECT ROUND(AVG(p.progressPercentage), 0) AS AverageProgressPercentage, ci.title FROM Progress p INNER JOIN ContentItem ci ON ci.ID = p.contentItemID INNER JOIN Module m ON m.contentItemID = ci.ID WHERE m.courseTitle = '"
                + course.getTitle() + "' GROUP BY ci.title, m.orderNumber ORDER BY m.orderNumber ASC";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        HashMap<String, Integer> data = new LinkedHashMap<String, Integer>();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int percentage = rs.getInt("AverageProgressPercentage");
                String title = rs.getString("title");

                // add to data to list
                data.put(title, percentage);

            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }

    // get the progress from all modules in a specific course
    public static HashMap<String, Integer> getCourseModulesProgressForUser(Course course, User user) {

        // set up variables
        String SQL = "SELECT p.progressPercentage, ci.title FROM Progress p INNER JOIN ContentItem ci ON ci.ID = p.contentItemID INNER JOIN Module m ON m.contentItemID = ci.ID WHERE m.courseTitle = '"
                + course.getTitle() + "' AND p.userEmail = '" + user.getEmail()
                + "' GROUP BY ci.title, m.orderNumber, p.progressPercentage ORDER BY m.orderNumber ASC";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        HashMap<String, Integer> data = new LinkedHashMap<String, Integer>();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int percentage = rs.getInt("progressPercentage");
                String title = rs.getString("title");

                // add to list
                data.put(title, percentage);

            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }

    // get the top 3 most viewed webcasts
    public static ObservableList<Webcast> getTop3Webcasts() {

        // set up variables
        String SQL = "SELECT TOP 3 COUNT(*) AS timesWatched, title, Progress.contentItemID, Webcast.ID AS webcastID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID INNER JOIN Progress ON ContentItem.ID = Progress.contentItemID GROUP BY Progress.contentItemID, title, Webcast.ID ORDER BY COUNT(*) DESC";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Webcast> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int webcastID = rs.getInt("webcastID");

                // add to list
                data.add(DatabaseWebcast.readWebcast(webcastID));
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }

    // get the top 3 courses with most enrollments
    public static ObservableList<Course> getTop3Courses() {

        // set up variables
        String SQL = "SELECT TOP 3 COUNT(*) AS obtainedCertificates, title FROM Course INNER JOIN Enrollment ON Course.title = Enrollment.courseTitle INNER JOIN Certificate ON Enrollment.ID = Certificate.enrollmentID GROUP BY Course.title ORDER BY COUNT(*) DESC";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Course> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                String title = rs.getString("title");

                data.add(DatabaseCourse.readCourse(title));
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }

    // get count of completions for a specific course
    public static int getCourseCompletionsCount(String courseTitle) {

        // set up variables
        String SQL = "SELECT COUNT(*) AS obtainedCertificates FROM Course INNER JOIN Enrollment ON Course.title = Enrollment.courseTitle INNER JOIN Certificate ON Enrollment.ID = Certificate.enrollmentID WHERE courseTitle = '"
                + courseTitle + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        int data = 0;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int obtainedCertificates = rs.getInt("obtainedCertificates");

                data = obtainedCertificates;
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }

    // get all certificates for a specific user
    public static ObservableList<Certificate> getUserCertificates(String userEmail) {

        // set up variables
        String SQL = "SELECT * FROM Certificate WHERE enrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail = '"
                + userEmail + "')";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Certificate> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int certificateID = rs.getInt("ID");

                // add to list
                data.add(DatabaseCertificate.readCertificate(certificateID));
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }

    // get all recommended courses for a specific course
    public static ObservableList<Course> getRecommendedCourses(String courseTitle) {

        // set up variables
        String SQL = "SELECT * FROM RecommendedCourse WHERE originalCourse = '" + courseTitle + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Course> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                String recommendedCourse = rs.getString("recommendedCourse");

                // add to list
                data.add(DatabaseCourse.readCourse(recommendedCourse));
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all connections
        finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }
}
