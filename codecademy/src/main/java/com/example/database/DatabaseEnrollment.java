package com.example.database;

import com.example.user.Enrollment;
import com.example.course.ContentItem;
import com.example.course.Course;
import com.example.course.Module;
import com.example.exeptions.AlreadyExistsException;
import com.example.user.Certificate;
import com.example.user.User;
import com.example.user.User.Gender;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;


public class DatabaseEnrollment extends Database{

    public static Enrollment readEnrollment(int id) {

        String SQL = "SELECT * FROM [Enrollment] WHERE ID = '" + id + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Enrollment data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitle = rs.getString("courseTitle");

                data = new Enrollment(idDB, enrollmentDate, courseTitle);
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

    public static String getEnrollmentUser(int id) {

        String SQL = "SELECT * FROM [Enrollment] WHERE ID = '" + id + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        String data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String userEmail = rs.getString("userEmail");

                data = userEmail;
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
    
    public static boolean createEnrollment(String userEmail, String courseTitle) throws AlreadyExistsException {

        if(checkIfExists(userEmail, courseTitle) == true){
            throw new AlreadyExistsException("The user \"" + userEmail + "\" has already enrolled in the course \"" + courseTitle + "\"");
        }

        LocalDate enrollmentDate = LocalDate.now();

        Course course = DatabaseCourse.readCourse(courseTitle);

        String SQL = "INSERT INTO [Enrollment] (enrollmentDate, userEmail, courseTitle) VALUES ('" + enrollmentDate + "', '" + userEmail + "', '" + courseTitle + "')";

        Connection con = getDbConnection();

        Statement stmt = null;

        try {

            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            for (ContentItem contentItem : course.getModules()) {

                Random rand = new Random();
                int randomNumber = rand.nextInt(101);

                DatabaseProgress.createProgress(randomNumber, userEmail, contentItem.getContentItemId());
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }

    }

    public static boolean deleteEnrollment(int id) {
        
        String SQL = "DELETE FROM [Enrollment] WHERE ID = '" + id + "'";

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

    public static ObservableList<Enrollment> getUserEnrollments(String courseTitle) {

        String SQL = "SELECT * FROM [Enrollment] WHERE courseTitle = '"+ courseTitle + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;

        final ObservableList<Enrollment> data = FXCollections.observableArrayList();

        try{

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();

                data.add(new Enrollment(idDB, enrollmentDate, courseTitle));
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

    public static ObservableList<Enrollment> getCourseEnrollments(String courseTitle) {

        String SQL = "SELECT * FROM [Enrollment] WHERE courseTitle = '"+ courseTitle + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;

        final ObservableList<Enrollment> data = FXCollections.observableArrayList();

        try{

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitleDB = rs.getString("courseTitle");

                data.add(new Enrollment(idDB, enrollmentDate, courseTitleDB));
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

    public static ObservableList<Enrollment> getEnrollmentsSearch(HashMap<String, String> searchArgs) {

        String SQL = Database.getSQLQuery("[Enrollment]", searchArgs);

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;

        final ObservableList<Enrollment> data = FXCollections.observableArrayList();

        try{

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitle = rs.getString("courseTitle");

                data.add(new Enrollment(idDB, enrollmentDate, courseTitle));
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

    public static boolean checkIfExists(String userEmail, String courseTitle) {

        String SQL = "SELECT * FROM [Enrollment] WHERE userEmail = '" + userEmail + "' AND courseTitle = '" + courseTitle + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Enrollment data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitleDB = rs.getString("courseTitle");

                data = new Enrollment(idDB, enrollmentDate, courseTitleDB);
                
            }
            
            if(data == null){
                return false;
            }else{
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }
}
