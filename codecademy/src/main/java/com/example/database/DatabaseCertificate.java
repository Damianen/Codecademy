package com.example.database;

import com.example.user.Certificate;
import com.example.user.Enrollment;
import com.example.ValidateFunctions;
import com.example.exeptions.AlreadyExistsException;

import java.sql.*;

public class DatabaseCertificate extends Database {

    // read certificate functions out of database with id
    public static Certificate readCertificate(int id) {

        // set up query
        String SQL = "SELECT * FROM [Certificate] WHERE ID = '" + id + "'";

        // get database connection
        Connection con = getDbConnection();

        // set up variables
        Statement stmt = null;
        ResultSet rs = null;
        Certificate data = null;

        try {
            // execute query and get the results
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int ratingDB = rs.getInt("rating");
                String employeeName = rs.getString("employeeName");

                // create new certificate from class
                data = new Certificate(id, ratingDB, employeeName);
            }

            // return data
            return data;

        }
        // handle any errors
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

    // create a certificate in the database
    public static boolean createCertificate(Enrollment enrollment) throws AlreadyExistsException {

        // check if enrollement already has a certificate
        if (enrollment.getCertificate() != null) {
            // throw error message
            throw new AlreadyExistsException("This enrollment already has a certificate");
        }

        // create a random enployee name and certificate rating
        String[] randomName = { "Alice", "Bob", "Charlie", "David" };
        int rangeEmployee = 3 - 0 + 1;
        int rangeRating = 10 - 1 + 1;
        int randEmployee = (int) (Math.random() * rangeEmployee) + 0;
        int randRating = (int) (Math.random() * rangeRating) + 1;

        // check if rating is valid
        try {
            ValidateFunctions.validateRating(rangeRating);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // set up query
        String SQL = "INSERT INTO [Certificate] VALUES ('" + randRating + "', '" + randomName[randEmployee] + "', '"
                + enrollment.getId() + "')";

        // get connections
        Connection con = getDbConnection();

        // set up variables
        Statement stmt = null;

        try {

            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
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

    // delete a certificate
    public static boolean deleteCertificate(int id) {

        // set up variables
        String SQL = "DELETE FROM [Certificate] WHERE ID = '" + id + "'";
        Connection con = getDbConnection();
        Statement stmt = null;

        try {

            // execute query
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            return true;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // close all connections
        finally {
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

    // get a certificate for a specific encrollment
    public static Certificate getEnrollmentCertificate(int enrollmentID) {

        // set up variables
        String SQL = "SELECT * FROM [Certificate] WHERE enrollmentID = " + enrollmentID;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Certificate data = null;

        try {

            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int idDB = rs.getInt("id");
                int ratingDB = rs.getInt("rating");
                String employeeName = rs.getString("employeeName");

                // create new certificate from class
                data = new Certificate(idDB, ratingDB, employeeName);
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
