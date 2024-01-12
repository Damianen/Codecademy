package com.example.database;

import com.example.user.Enrollment;
import com.example.ValidateFunctions;
import com.example.course.ContentItem;
import com.example.course.Course;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

public class DatabaseEnrollment extends Database {

    // read enrollment from database
    public static Enrollment readEnrollment(int id) {

        // set up variables
        String SQL = "SELECT * FROM [Enrollment] WHERE ID = '" + id + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Enrollment data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitle = rs.getString("courseTitle");

                // create enrollment form class
                data = new Enrollment(idDB, enrollmentDate, courseTitle);
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

    // get user for specific enrollment
    public static String getEnrollmentUser(int id) {

        // set up variables
        String SQL = "SELECT * FROM [Enrollment] WHERE ID = '" + id + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String userEmail = rs.getString("userEmail");

                data = userEmail;
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

    // create enrollment in database
    public static boolean createEnrollment(String userEmail, String courseTitle) throws AlreadyExistsException {

        // chekc if enrollment already exists
        if (checkIfExists(userEmail, courseTitle) == true) {
            throw new AlreadyExistsException(
                    "The user \"" + userEmail + "\" has already enrolled in the course \"" + courseTitle + "\"");
        }

        // set up variables
        LocalDate enrollmentDate = LocalDate.now();
        Course course = DatabaseCourse.readCourse(courseTitle);
        String SQL = "INSERT INTO [Enrollment] (enrollmentDate, userEmail, courseTitle) VALUES ('" + enrollmentDate
                + "', '" + userEmail + "', '" + courseTitle + "')";
        Connection con = getDbConnection();
        Statement stmt = null;

        try {
            // execute query
            stmt = con.createStatement();
            stmt.executeUpdate(SQL);

            // for each modules in the course automaticly create a progress
            for (ContentItem contentItem : course.getModules()) {

                Random rand = new Random();
                int randomNumber = rand.nextInt(101);

                // validate progress percentage
                if (ValidateFunctions.isValidPercentage(randomNumber) != true) {
                    throw new IllegalArgumentException(randomNumber + " is not a valid progress perentage");
                }

                // create progress from class
                DatabaseProgress.createProgress(randomNumber, userEmail, contentItem.getContentItemId());
            }

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

    // delet enrollemnt in database
    public static boolean deleteEnrollment(int id) {

        // set up variables
        String SQL = "DELETE FROM [Enrollment] WHERE ID = '" + id + "'";
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

    // get all enrollemnts for a specific user
    public static ObservableList<Enrollment> getUserEnrollments(String courseTitle) {

        // set up variables
        String SQL = "SELECT * FROM [Enrollment] WHERE courseTitle = '" + courseTitle + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Enrollment> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();

                // create enrollment from class
                data.add(new Enrollment(idDB, enrollmentDate, courseTitle));
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

    // get all enrollments for a course from the database
    public static ObservableList<Enrollment> getCourseEnrollments(String courseTitle) {

        // set up variables
        String SQL = "SELECT * FROM [Enrollment] WHERE courseTitle = '" + courseTitle + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Enrollment> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitleDB = rs.getString("courseTitle");

                // create enrollment from class and add to list
                data.add(new Enrollment(idDB, enrollmentDate, courseTitleDB));
            }

            return data;

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        // close database connections
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

    // get enrollmens based on list items
    public static ObservableList<Enrollment> getEnrollmentsSearch(HashMap<String, String> searchArgs) {

        // get query
        String SQL = Database.getSQLQuery("[Enrollment]", searchArgs);

        // set up query
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Enrollment> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitle = rs.getString("courseTitle");

                // create enrollment with class and add to list
                data.add(new Enrollment(idDB, enrollmentDate, courseTitle));
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

    // check if enrollment exists
    public static boolean checkIfExists(String userEmail, String courseTitle) {

        // set up variables
        String SQL = "SELECT * FROM [Enrollment] WHERE userEmail = '" + userEmail + "' AND courseTitle = '"
                + courseTitle + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Enrollment data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int idDB = rs.getInt("ID");
                LocalDate enrollmentDate = rs.getDate("enrollmentDate").toLocalDate();
                String courseTitleDB = rs.getString("courseTitle");

                // create enrollment form class
                data = new Enrollment(idDB, enrollmentDate, courseTitleDB);

            }

            if (data == null) {
                return false;
            } else {
                return true;
            }

        }
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return true;
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
