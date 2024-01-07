package com.example.database;

import com.example.course.Course;
import com.example.course.Module;
import com.example.course.Course.DifficultyLevel;
import com.example.user.User.Gender;

import java.sql.*;
import java.util.HashMap;

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
        
        String SQL = "SELECT ROUND(AVG(p.progressPercentage), 0) AS AverageProgressPercentage, ci.title, m.orderNumber FROM Progress p INNER JOIN ContentItem ci ON ci.ID = p.contentItemID INNER JOIN Module m ON m.contentItemID = ci.ID WHERE m.courseTitle = '" + course.getTitle() + "' GROUP BY ci.title, m.orderNumber ORDER BY m.orderNumber ASC";

        Connection con = getDbConnection();
    
        Statement stmt = null;
        ResultSet rs = null;
        HashMap<String, Integer> data = new HashMap<String, Integer>();   


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

    
}
