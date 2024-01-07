package com.example.database;

import com.example.user.Enrollment;
import com.example.user.User;
import com.example.user.User.Gender;
import com.example.ValidateFunctions;
import com.example.course.Course;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

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
                String postalCodeDB = rs.getString("postalCode");
                String residenceDB = rs.getString("residence");
                String countryDB = rs.getString("country");

                data = new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB, countryDB);
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
    
    public static boolean createUser(String email, String name, LocalDate dateOfBirth, Gender gender, String address, String postalCode, String residence, String country) throws AlreadyExistsException {
        boolean isEmailValid = ValidateFunctions.validateMailAddress(email);
        
        if(isEmailValid == false){
           throw new IllegalArgumentException("The email \"" + email + "\" is invalid");
        }
    

        if(readUser(email) != null){
            throw new AlreadyExistsException("The email \"" + email + "\" already exists for user");
        }

        if(ValidateFunctions.validateDate(dateOfBirth.getDayOfMonth(), dateOfBirth.getMonth(), dateOfBirth.getYear()) == false){
            throw new IllegalArgumentException("The Date \"" + dateOfBirth + "\" is invalid");
        } 


        String SQL = "INSERT INTO [User] VALUES ('" + email + "', '" + name + "', '" + dateOfBirth + "', '" + gender + "', '" + address + "', '" + postalCode + "', '" + residence + "', '" + country + "')";

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

    public static boolean updateUser(String email, String newEmail, String newName, LocalDate newDateOfBirth, Gender newGender, String newAddress, String postalCode, String newResidence, String newCountry) throws AlreadyExistsException {

        if(ValidateFunctions.validateMailAddress(newEmail) == false){
            throw new IllegalArgumentException("The email \"" + newEmail + "\" is invalid");
        }

        if(ValidateFunctions.validateDate(newDateOfBirth.getDayOfMonth(), newDateOfBirth.getMonth(), newDateOfBirth.getYear()) == false){
            throw new IllegalArgumentException("The Date \"" + newDateOfBirth + "\" is invalid");
        } 
        
        if(readUser(newEmail) != null){
            throw new AlreadyExistsException("The email \"" + newEmail + "\" already exists for user");
        }

        String SQL = "UPDATE [User] SET email = '" + newEmail + "', name = '" + newName + "', dateOfBirth = '" + newDateOfBirth + "', gender = '" + newGender + "', address = '" + newAddress + "', postalCode = '" + postalCode + "', residence = '" + newResidence + "', country = '" + newCountry + "' WHERE email = '" + email + "'";

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

    public static final ObservableList<User> getUserListSearch(String nameSearch) {

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
                String postalCodeDB = rs.getString("postalCode");
                String residenceDB = rs.getString("residence");
                String countryDB = rs.getString("country");

                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB, countryDB));
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

    public static final ObservableList<User> getUserList() {

        String SQL = "SELECT * FROM [User]";

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
                String postalCodeDB = rs.getString("postalCode");
                String residenceDB = rs.getString("residence");
                String countryDB = rs.getString("country");

                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB, countryDB));
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

    public static final ObservableList<User> readUserSearchAll(HashMap<String, String> searchArgs) {

        String SQL = Database.getSQLQuery("[User]", searchArgs);

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
                String postalCodeDB = rs.getString("postalCode");
                String residenceDB = rs.getString("residence");
                String countryDB = rs.getString("country");

                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB, countryDB));
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
    
    public static final ObservableList<User> getEnrolledUsersForCourse(Course course) {

        String SQL = "SELECT * FROM [User] WHERE email IN (SELECT userEmail FROM Enrollment WHERE courseTitle = '" + course.getTitle() + "')";

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
                String postalCodeDB = rs.getString("postalCode");
                String residenceDB = rs.getString("residence");
                String countryDB = rs.getString("country");

                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB, countryDB));
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
