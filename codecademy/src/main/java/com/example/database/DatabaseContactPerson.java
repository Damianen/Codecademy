package com.example.database;

import com.example.ValidateFunctions.ValidateFunctions;
import com.example.course.ContactPerson;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseContactPerson extends Database {

    // get list of all contact people
    public static final ObservableList<ContactPerson> getContactPersonList() {

        // set up variables
        String SQL = "SELECT * FROM contactPerson";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<ContactPerson> data = FXCollections.observableArrayList();

        try {

            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");

                // create contact person from class and add to list
                data.add(new ContactPerson(email, name));
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

    // get a contact person out of the database with email
    public static ContactPerson readContactPerson(String email) {

        // set up variables
        String SQL = "SELECT * FROM [ContactPerson] WHERE email = '" + email + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ContactPerson data = null;

        try {

            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String emailDB = rs.getString("email");
                String nameDB = rs.getString("name");

                // create new contact person from class
                data = new ContactPerson(emailDB, nameDB);
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

    // get all contact people exect one
    public static ObservableList<ContactPerson> readForNewContactPerson(String email) {

        // set up variables
        String SQL = "SELECT * FROM [ContactPerson] WHERE NOT email = '" + email + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<ContactPerson> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String emailDB = rs.getString("email");
                String nameDB = rs.getString("name");

                // create new contact person from class
                data.add(new ContactPerson(emailDB, nameDB));
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

    // create a contact person in the database
    public static boolean createContactPerson(String email, String name) throws AlreadyExistsException {

        // check if email is valid
        if (ValidateFunctions.validateMailAddress(email) == false) {
            throw new IllegalArgumentException("The email \"" + email + "\" is invalid");
        }

        // check if contactperson doesn't already exist
        if (readContactPerson(email) != null) {
            throw new AlreadyExistsException("The email \"" + email + "\" already exists for contact person");
        }

        // set up variables
        String SQL = "INSERT INTO [ContactPerson] VALUES ('" + email + "', '" + name + "')";
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

    // update a contact person in the database
    public static boolean updateContactPerson(String email, String newEmail, String newName)
            throws AlreadyExistsException {

        // check if contactperson doesn't already exist
        if (ValidateFunctions.validateMailAddress(email) == false) {
            throw new IllegalArgumentException("The email \"" + email + "\" is invalid");
        }

        // check if email is valid
        if (readContactPerson(newEmail) != null) {
            throw new AlreadyExistsException("The email \"" + newEmail + "\" already exists for contact person");
        }

        // set up variables
        String SQL = "UPDATE [ContactPerson] SET email = '" + newEmail + "', name = '" + newName + "' WHERE email = '"
                + email + "'";
        Connection con = getDbConnection();
        Statement stmt = null;

        try {

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

    // delete contact person from database
    public static boolean deleteContactPerson(String email) {

        // set up variables
        String SQL = "DELETE FROM [ContactPerson] WHERE email = '" + email + "'";
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
}
