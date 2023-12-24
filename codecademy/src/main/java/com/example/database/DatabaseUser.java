package com.example.database;

import com.example.user.User;
import com.example.user.User.Gender;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseUser extends Database{

    public static User readUser(String email) {

        String SQL = "SELECT * FROM [User] WHERE email = '" + email + "'";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        User data = null;

        try {

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

                data = new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, residenceDB, countryDB);
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
    
    public static boolean createUser(String email, String name, LocalDate dateOfBirth, Gender gender, String address, String residence, String country) throws AlreadyExistsException {

        if(readUser(email) != null){
            throw new AlreadyExistsException("The email \"" + email + "\" already exists");
        }

        String SQL = "INSERT INTO [User] VALUES ('" + email + "', '" + name + "', '" + dateOfBirth + "', '" + gender + "', '" + address + "', '" + residence + "', '" + country + "')";

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

    public static boolean updateUser(String email, String newEmail, String newName, LocalDate newDateOfBirth, Gender newGender, String newAddress, String newResidence, String newCountry) throws AlreadyExistsException {

        if(readUser(newEmail) != null){
            throw new AlreadyExistsException("The email \"" + email + "\" already exists");
        }

        String SQL = "UPDATE [User] SET email = '" + newEmail + "', name = '" + newName + "', dateOfBirth = '" + newDateOfBirth + "', gender = '" + newGender + "', address = '" + newAddress + "', residence = '" + newResidence + "', country = '" + newCountry + "' WHERE email = '" + email + "'";

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

    public static boolean deleteUser(String email) {
        
        String SQL = "DELETE FROM [User] WHERE email = '" + email + "'";

        Connection con = getDbConnection();

        Statement stmt = null;

        User user = readUser(email);

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

    public static final ObservableList<User> getUserList(String nameSearch) {

        String SQL = "SELECT * FROM [User] WHERE name LIKE '%" + nameSearch + "%'";

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
}
