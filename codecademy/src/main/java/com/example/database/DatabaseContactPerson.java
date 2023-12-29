package com.example.database;

import com.example.user.Enrollment;
import com.example.user.User;
import com.example.user.User.Gender;
import com.example.course.ContactPerson;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseContactPerson extends Database{

    public static ContactPerson readContactPerson(String email) {

        String SQL = "SELECT * FROM [ContactPerson] WHERE email = '" + email + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        ContactPerson data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String emailDB = rs.getString("email");
                String nameDB = rs.getString("name");

                data = new ContactPerson(emailDB, nameDB);
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
    
    public static boolean createContactPerson(String email, String name) throws AlreadyExistsException {

        if(readContactPerson(email) != null){
            throw new AlreadyExistsException("The email \"" + email + "\" already exists for contact person");
        }

        String SQL = "INSERT INTO [ContactPerson] VALUES ('" + email + "', '" + name + "')";

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

    public static boolean updateContactPerson(String email, String newEmail, String newName) throws AlreadyExistsException {

        if(readContactPerson(newEmail) != null){
            throw new AlreadyExistsException("The email \"" + newEmail + "\" already exists for contact person");
        }

        String SQL = "UPDATE [ContactPerson] SET email = '" + newEmail + "', name = '" + newName + "' WHERE email = '" + email + "'";

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

    public static boolean deleteContactPerson(String email) {
        
        String SQL = "DELETE FROM [ContactPerson] WHERE email = '" + email + "'";

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
}
