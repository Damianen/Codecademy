package com.example.database;

import com.example.course.Course;
import com.example.course.Course.DifficultyLevel;
import com.example.exeptions.AlreadyExistsException;
import com.example.exeptions.CannotBeEmptyException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;

public class DatabaseCourse extends Database {

    // create course in database
    public static boolean createCourse(String title, String subject, String introText, DifficultyLevel difficultyLevel)
            throws AlreadyExistsException, CannotBeEmptyException {

        // check if nothing is empty
        if (title.isEmpty()) {
            throw new CannotBeEmptyException("Title cannot be empty!");
        } else if (subject.isEmpty()) {
            throw new CannotBeEmptyException("Subject cannot be empty!");
        } else if (introText.isEmpty()) {
            throw new CannotBeEmptyException("Intro text cannot be empty!");
        } else if (difficultyLevel == null) {
            throw new CannotBeEmptyException("Choose a difficulty level!");
        }

        // check if course already exists
        if (readCourse(title) != null) {
            // throw error
            throw new AlreadyExistsException("The course \"" + title + "\" already exists");
        }

        // set up variables
        String SQL = "INSERT INTO Course VALUES ('" + title + "', '" + subject + "', '" + introText + "', '"
                + difficultyLevel
                + "')";
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

    // read course form database
    public static Course readCourse(String title) {

        // set up variables
        String SQL = "SELECT * FROM Course WHERE title = '" + title + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Course data = null;

        try {

            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String titleDB = rs.getString("title");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("difficultyLevel"));

                // create new course from class
                data = new Course(titleDB, subjectDB, introTextDB, difficultyLevelDB);
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

    // update course in database
    public static boolean updateCourse(String title, String newtitle, String newSubject, String newIntroText,
            DifficultyLevel newDifficultyLevel) throws AlreadyExistsException {

        // check if new course title already exists
        if (readCourse(newtitle) != null) {
            throw new AlreadyExistsException("The course \"" + title + "\" already exists");
        }

        // set up variables
        String SQL = "UPDATE Course SET title = '" + newtitle + "', subject = '" + newSubject + "', introText = '"
                + newIntroText + "', difficultyLevel = '" + newDifficultyLevel + "' WHERE title = '" + title + "'";
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

    // delete course form database
    public static boolean deleteCourse(String title) {

        // set up variables
        String SQL = "DELETE FROM Course WHERE title = '" + title + "'";
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

    // get course list based on a title search
    public static final ObservableList<Course> getCourseListSearch(String titleSearch) {

        // set up variables
        String SQL = "SELECT * FROM Course WHERE title LIKE '%" + titleSearch + "%'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Course> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String titleDB = rs.getString("title");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("difficultyLevel"));

                // create new course from class and add to list
                data.add(new Course(titleDB, subjectDB, introTextDB, difficultyLevelDB));
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

    // get list with courses from database
    public static final ObservableList<Course> getCourseList() {

        // set up variables
        String SQL = "SELECT * FROM Course";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Course> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String titleDB = rs.getString("title");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("difficultyLevel"));

                // create course from class and add to list
                data.add(new Course(titleDB, subjectDB, introTextDB, difficultyLevelDB));
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

    // get course list bases on array of items
    public static ObservableList<Course> readCourseSearchAll(HashMap<String, String> searchArgs) {

        // get query
        String SQL = Database.getSQLQuery("course", searchArgs);

        // set up variable
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Course> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String titleDB = rs.getString("title");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("difficultyLevel"));

                // create course from class and add to list
                data.add(new Course(titleDB, subjectDB, introTextDB, difficultyLevelDB));
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

    // get course list from database where a specific user is not enrolled in
    public static final ObservableList<Course> getNotEnrolledCourseForUser(String userEmail) {

        // set up variables
        String SQL = "SELECT * FROM Course WHERE title NOT IN (SELECT courseTitle FROM Enrollment WHERE userEmail = '"
                + userEmail + "')";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Course> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String titleDB = rs.getString("title");
                String subjectDB = rs.getString("subject");
                String introTextDB = rs.getString("introText");
                DifficultyLevel difficultyLevelDB = DifficultyLevel.valueOf(rs.getString("difficultyLevel"));

                // create course from class and add to list
                data.add(new Course(titleDB, subjectDB, introTextDB, difficultyLevelDB));
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

    // create a recommended course in database
    public static boolean createRecommendCourse(String originalCourse, String recommendedCourse) {

        // set up variables
        String SQL = "INSERT INTO RecommendedCourse VALUES ('" + originalCourse + "', '" + recommendedCourse + "')";
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
        // close al connections
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
