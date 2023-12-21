package com.example.database;

import com.example.course.Course;
import com.example.course.Course.DifficultyLevel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseCourse extends Database{
    
    public static boolean createCourse(String name, String subject, String introText, DifficultyLevel difficultyLevel) {

        String SQL = "INSERT INTO Course VALUES ('" + name + "', '" + subject + "', '" + introText + "', '" + difficultyLevel
                + "')";

        Connection con = getDbConnection();

        Statement stmt = null;

        try {

            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }

    }

    public static Course readCourse(String name) {

        String SQL = "SELECT * FROM Course WHERE name = '" + name + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Course data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String nameDB = rs.getString("name");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("level"));

                data = new Course(nameDB, subjectDB, introTextDB, difficultyLevelDB);
            }
            
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    public static boolean updateCourse(String name, String newName, String newSubject, String newIntroText, DifficultyLevel newDifficultyLevel) {

        String SQL = "UPDATE Course SET name = '" + newName + "', subject = '" + newSubject + "', introText = '" + newIntroText + "', level = '" + newDifficultyLevel + "' WHERE name = '" + name + "'";

        Connection con = getDbConnection();

        Statement stmt = null;

        try {

            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }

    }

    public static boolean deleteCourse(String name) {
        
        String SQL = "DELETE FROM Course WHERE name = '" + name + "'";

        Connection con = getDbConnection();

        Statement stmt = null;

        try {

            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }

    }

    public static final ObservableList<Course> getCourseList(String nameSearch) {

        String SQL = "SELECT * FROM Course WHERE name LIKE '%" + nameSearch + "%'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;

        final ObservableList<Course> data = FXCollections.observableArrayList();

        try{

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String nameDB = rs.getString("name");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("level"));

                data.add(new Course(nameDB, subjectDB, introTextDB, difficultyLevelDB));
            }

            return data;

        }catch(Exception e){
            e.printStackTrace();
            return data;
        }finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }
}