package com.example.database;

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

public class DatabaseUser extends Database {

    // read user from database
    public static User readUser(String email) {

        // set up variables
        String SQL = "SELECT * FROM [User] WHERE email = '" + email + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        User data = null;

        try {
            // execute query
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

                // create user form class
                data = new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB,
                        countryDB);
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

    // create a user in the database
    public static boolean createUser(String email, String name, LocalDate dateOfBirth, Gender gender, String address,
            String postalCode, String residence, String country) throws AlreadyExistsException {

        // validate email
        if (ValidateFunctions.validateMailAddress(email) != true) {
            throw new IllegalArgumentException("The email \"" + email + "\" is invalid");
        }

        // check if user already exists
        if (readUser(email) != null) {
            throw new AlreadyExistsException("The email \"" + email + "\" already exists for user");
        }

        // validate date
        if (ValidateFunctions.validateDate(dateOfBirth.getDayOfMonth(), dateOfBirth.getMonthValue(),
                dateOfBirth.getYear()) != true) {
            throw new IllegalArgumentException("The Date \"" + dateOfBirth + "\" is invalid");
        }

        // format postal code
        try {
            postalCode = ValidateFunctions.formatPostalCode(postalCode);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // set up variables
        String SQL = "INSERT INTO [User] VALUES ('" + email + "', '" + name + "', '" + dateOfBirth + "', '" + gender
                + "', '" + address + "', '" + postalCode + "', '" + residence + "', '" + country + "')";
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

    // update user in the database
    public static boolean updateUser(String email, String newEmail, String newName, LocalDate newDateOfBirth,
            Gender newGender, String newAddress, String postalCode, String newResidence, String newCountry)
            throws AlreadyExistsException {

        // validate email
        if (ValidateFunctions.validateMailAddress(newEmail) == false) {
            throw new IllegalArgumentException("The email \"" + newEmail + "\" is invalid");
        }

        // validate date
        if (ValidateFunctions.validateDate(newDateOfBirth.getDayOfMonth(), newDateOfBirth.getMonthValue(),
                newDateOfBirth.getYear()) != true) {
            throw new IllegalArgumentException("The Date \"" + newDateOfBirth + "\" is invalid");
        }

        // check if user exists
        if (readUser(newEmail) != null) {
            throw new AlreadyExistsException("The email \"" + newEmail + "\" already exists for user");
        }

        // format postal code
        try {
            postalCode = ValidateFunctions.formatPostalCode(postalCode);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // set up variables
        String SQL = "UPDATE [User] SET email = '" + newEmail + "', name = '" + newName + "', dateOfBirth = '"
                + newDateOfBirth + "', gender = '" + newGender + "', address = '" + newAddress + "', postalCode = '"
                + postalCode + "', residence = '" + newResidence + "', country = '" + newCountry + "' WHERE email = '"
                + email + "'";
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

    // deletes a user in database
    public static boolean deleteUser(String email) {

        // set up variables
        String SQL = "DELETE FROM [User] WHERE email = '" + email + "'";
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

    // get user list with search
    public static final ObservableList<User> getUserListSearch(String nameSearch) {

        // set up variables
        String SQL = "SELECT * FROM [User] WHERE name LIKE '%" + nameSearch + "%'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<User> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // create user from class and add to list
                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB,
                        countryDB));
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

    // get all users in a list
    public static final ObservableList<User> getUserList() {

        // set up variables
        String SQL = "SELECT * FROM [User]";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<User> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // create user from class and add to list
                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB,
                        countryDB));
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

    // get user list based on list arguments
    public static final ObservableList<User> readUserSearchAll(HashMap<String, String> searchArgs) {

        // set up variables
        String SQL = Database.getSQLQuery("[User]", searchArgs);
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<User> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // create user from class and add to list
                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB,
                        countryDB));
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

    // get enrolled all enrolled user in a specific course
    public static final ObservableList<User> getEnrolledUsersForCourse(Course course) {

        // set up query
        String SQL = "SELECT * FROM [User] WHERE email IN (SELECT userEmail FROM Enrollment WHERE courseTitle = '"
                + course.getTitle() + "')";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<User> data = FXCollections.observableArrayList();

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

                // create user from class and add to list
                data.add(new User(emailDB, nameDB, dateOfBirthDB, genderDB, addressDB, postalCodeDB, residenceDB,
                        countryDB));
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close all values
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
