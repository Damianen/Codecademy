package com.example.database;

import com.example.course.Course;
import com.example.course.Course.DifficultyLevel;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseCourse extends Database{
    
    public static boolean createCourse(String title, String subject, String introText, DifficultyLevel difficultyLevel) throws AlreadyExistsException {

        if(readCourse(title) != null){
            throw new AlreadyExistsException("The course \"" + title + "\" already exists");
        }

        String SQL = "INSERT INTO Course VALUES ('" + title + "', '" + subject + "', '" + introText + "', '" + difficultyLevel
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

    public static Course readCourse(String title) {

        String SQL = "SELECT * FROM Course WHERE title = '" + title + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Course data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String titleDB = rs.getString("title");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("difficultyLevel"));

                data = new Course(titleDB, subjectDB, introTextDB, difficultyLevelDB);
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

    public static boolean updateCourse(String title, String newtitle, String newSubject, String newIntroText, DifficultyLevel newDifficultyLevel) throws AlreadyExistsException {

        if(readCourse(newtitle) != null){
            throw new AlreadyExistsException("The course \"" + title + "\" already exists");
        }

        String SQL = "UPDATE Course SET title = '" + newtitle + "', subject = '" + newSubject + "', introText = '" + newIntroText + "', difficultyLevel = '" + newDifficultyLevel + "' WHERE title = '" + title + "'";

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

    public static boolean deleteCourse(String title) {
        
        String SQL = "DELETE FROM Course WHERE title = '" + title + "'";

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

    public static final ObservableList<Course> getCourseList(String titleSearch) {

        String SQL = "SELECT * FROM Course WHERE title LIKE '%" + titleSearch + "%'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;

        final ObservableList<Course> data = FXCollections.observableArrayList();

        try{

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String titleDB = rs.getString("title");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("difficultyLevel"));

                data.add(new Course(titleDB, subjectDB, introTextDB, difficultyLevelDB));
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
