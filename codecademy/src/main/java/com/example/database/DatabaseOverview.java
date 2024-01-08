package com.example.database;

import com.example.course.ContentItem.Status;
import com.example.course.Course;
import com.example.course.Module;
import com.example.course.Webcast;
import com.example.course.Course.DifficultyLevel;
import com.example.user.Certificate;
import com.example.user.User;
import com.example.user.User.Gender;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseOverview extends Database {
    
    public static int getObtaintedCertificatesPercentageForGender(Gender gender) {
        
        String SQL = "SELECT ROUND(100 / (SELECT count(*) FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = '"+ gender +"')) * (SELECT count(*) FROM Certificate WHERE EnrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail IN (SELECT email FROM [User] WHERE Gender = '" + gender +"'))), 0) AS Percentage";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        int data = 0;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int percentage = rs.getInt("Percentage");

                data = percentage;
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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

    public static HashMap<String, Integer> getAveragePogressPercentagePerCourseModule(Course course) {
        
        String SQL = "SELECT ROUND(AVG(p.progressPercentage), 0) AS AverageProgressPercentage, ci.title FROM Progress p INNER JOIN ContentItem ci ON ci.ID = p.contentItemID INNER JOIN Module m ON m.contentItemID = ci.ID WHERE m.courseTitle = '" + course.getTitle() + "' GROUP BY ci.title, m.orderNumber ORDER BY m.orderNumber ASC";

        Connection con = getDbConnection();
    
        Statement stmt = null;
        ResultSet rs = null;
        HashMap<String, Integer> data = new LinkedHashMap<String, Integer>();   


        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int percentage = rs.getInt("AverageProgressPercentage");
                String title = rs.getString("title");

                data.put(title, percentage);

            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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

    public static HashMap<String, Integer> getCourseModulesProgressForUser(Course course, User user) {

        //moet nog gemaakt worden
        
        String SQL = "SELECT p.progressPercentage, ci.title FROM Progress p INNER JOIN ContentItem ci ON ci.ID = p.contentItemID INNER JOIN Module m ON m.contentItemID = ci.ID WHERE m.courseTitle = '" + course.getTitle() + "' AND p.userEmail = '" + user.getEmail() + "' GROUP BY ci.title, m.orderNumber, p.progressPercentage ORDER BY m.orderNumber ASC";

        Connection con = getDbConnection();
    
        Statement stmt = null;
        ResultSet rs = null;
        HashMap<String, Integer> data = new LinkedHashMap<String, Integer>();   


        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int percentage = rs.getInt("progressPercentage");
                String title = rs.getString("title");

                data.put(title, percentage);

            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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

    public static ObservableList<Webcast> getTop3Webcasts() {
        
        String SQL = "SELECT TOP 3 COUNT(*) AS timesWatched, title, Progress.contentItemID, Webcast.ID AS webcastID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID INNER JOIN Progress ON ContentItem.ID = Progress.contentItemID GROUP BY Progress.contentItemID, title, Webcast.ID ORDER BY COUNT(*) DESC";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Webcast> data = FXCollections.observableArrayList();

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int webcastID = rs.getInt("webcastID");
                
                data.add(DatabaseWebcast.readWebcast(webcastID));
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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

    public static ObservableList<Course> getTop3Courses() {
        
        String SQL = "SELECT TOP 3 COUNT(*) AS obtainedCertificates, title FROM Course INNER JOIN Enrollment ON Course.title = Enrollment.courseTitle INNER JOIN Certificate ON Enrollment.ID = Certificate.enrollmentID GROUP BY Course.title ORDER BY COUNT(*) DESC";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Course> data = FXCollections.observableArrayList();

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                String title = rs.getString("title");
                
                data.add(DatabaseCourse.readCourse(title));
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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

    public static int getCourseCompletionsCount(String courseTitle) {
        
        String SQL = "SELECT COUNT(*) AS obtainedCertificates FROM Course INNER JOIN Enrollment ON Course.title = Enrollment.courseTitle INNER JOIN Certificate ON Enrollment.ID = Certificate.enrollmentID WHERE courseTitle = '" + courseTitle + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        int data = 0;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int obtainedCertificates = rs.getInt("obtainedCertificates");
                
                data = obtainedCertificates;
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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
    
    public static ObservableList<Certificate> getUserCertificates(String userEmail) {
        
        String SQL = "SELECT * FROM Certificate WHERE enrollmentID IN (SELECT ID FROM Enrollment WHERE userEmail = '" + userEmail + "')";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Certificate> data = FXCollections.observableArrayList();

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int certificateID = rs.getInt("ID");
                
                data.add(DatabaseCertificate.readCertificate(certificateID));
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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

    public static ObservableList<Course> getRecommendedCourses(String courseTitle) {
        
        String SQL = "SELECT * FROM RecommendedCourse WHERE originalCourse = '" + courseTitle + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Course> data = FXCollections.observableArrayList();

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                String recommendedCourse = rs.getString("recommendedCourse");
                
                data.add(DatabaseCourse.readCourse(recommendedCourse));
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        } finally {
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
