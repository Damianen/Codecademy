package com.example.database;

import com.example.user.Certificate;
import com.example.user.Enrollment;
import com.example.user.User;
import com.example.user.User.Gender;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseCertificate extends Database{

    public static Certificate readCertificate(int id) {

        String SQL = "SELECT * FROM [Certificate] WHERE ID = '" + id + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Certificate data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int ratingDB = rs.getInt("rating");
                String employeeName = rs.getString("employeeName");

                data = new Certificate(id, ratingDB, employeeName);
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
    
    public static boolean createCertificate(Enrollment enrollment) throws AlreadyExistsException {

        if(enrollment.getCertificate() != null){
            throw new AlreadyExistsException("This enrollment already has a certificate");
        }

        String[] randomName = {"Alice", "Bob", "Charlie", "David"};
            
        int rangeEmployee = 3 - 0 + 1;
        int rangeRating = 10 - 1 + 1;

        int randEmployee = (int) (Math.random() * rangeEmployee) + 0;
        int randRating = (int) (Math.random() * rangeRating) + 1;
            
        String SQL = "INSERT INTO [Certificate] VALUES ('" + randRating + "', '" + randomName[randEmployee] + "', '" + enrollment.getId() + "')";

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

    public static boolean deleteCertificate(int id) {
        
        String SQL = "DELETE FROM [Certificate] WHERE ID = '" + id + "'";

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

    public static final ObservableList<User> getUserCertificates(String userEmail) {

        String SQL = "SELECT * FROM [Certificate] WHERE name LIKE '%" + userEmail + "%'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;

        final ObservableList<User> data = FXCollections.observableArrayList();

        try{

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String emailDB = rs.getString("email");
                String nameDB = rs.getString("name");
                LocalDate dateOfBirthDB = rs.getDate("dateOfBirth").toLocalDate();
                Gender genderDB = Gender.valueOf(rs.getString("gender"));
                String addressDB = rs.getString("address");
                String residenceDB = rs.getString("residence");
                String countryDB = rs.getString("country");

                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, residenceDB, countryDB));
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

    public static Certificate getEnrollmentCertificate(int enrollmentID) {

        String SQL = "SELECT * FROM [Certificate] WHERE enrollmentID = '" + enrollmentID + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Certificate data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int idDB = rs.getInt("id");
                int ratingDB = rs.getInt("rating");
                String employeeName = rs.getString("employeeName");

                data = new Certificate(idDB, ratingDB, employeeName);
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

    
}
