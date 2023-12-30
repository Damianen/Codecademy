package com.example.database;

import com.example.user.Enrollment;
import com.example.exeptions.AlreadyExistsException;
import com.example.user.Certificate;
import com.example.user.User;
import com.example.user.User.Gender;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import java.time.LocalDate;
import java.util.HashMap;


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
    
    public static boolean createEnrollment(String userEmail, String courseTitle) throws AlreadyExistsException {

        if(checkIfExists(userEmail, courseTitle) == true){
            throw new AlreadyExistsException("The user \"" + userEmail + "\" has already enrolled in the course \"" + courseTitle + "\"");
        }

        LocalDate enrollmentDate = LocalDate.now();

        String SQL = "INSERT INTO [Enrollment] (enrollmentDate, userEmail, courseTitle, certificateID) VALUES ('" + enrollmentDate + "', '" + userEmail + "', '" + courseTitle + "', " + null + ")";

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

    public static ObservableList<Enrollment> getCourseEnrollments(String userEmail) {

        String SQL = "SELECT * FROM [Enrollment] WHERE userEmail = '"+ userEmail + "'";

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

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            
            if(rs == null){
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
