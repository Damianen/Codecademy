package com.example.database;

import com.example.user.Progress;
import com.example.ValidateFunctions.ValidateFunctions;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseProgress extends Database {

    // read a progress from database
    public static Progress readProgress(int id) {

        // set up variables
        String SQL = "SELECT * FROM [Progress] WHERE id = " + id;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Progress data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int progressPercentage = rs.getInt("progressPercentage");
                int contentItemID = rs.getInt("contentItemID");

                // create progress from class
                data = new Progress(id, progressPercentage, contentItemID);
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

    // create progress in database
    public static boolean createProgress(int progressPercentage, String userEmail, int contentItemID)
            throws AlreadyExistsException {

        String contentItemTitle = null;

        // get content item type and get the title
        switch (DatabaseContentItem.getContentItemType(contentItemID)) {
            case "Webcast":
                contentItemTitle = DatabaseWebcast.readWebcastWithContentItemID(contentItemID).getTitle();
                break;

            case "Module":
                contentItemTitle = DatabaseModule.readModuleWithContentItemID(contentItemID).getTitle();
                break;
        }

        // check if progress already exists
        if (getProgressWithUserAndContentItem(userEmail, contentItemID) != null) {
            throw new AlreadyExistsException(
                    "Progress between \"" + userEmail + "\" and \"" + contentItemTitle + "\" already exists");
        }

        // validate progress percentage
        if (ValidateFunctions.isValidPercentage(progressPercentage) == false) {
            throw new IllegalArgumentException("The percentage \"" + progressPercentage + "\" is invalid");
        }

        // set up variables
        String SQL = "INSERT INTO [Progress] VALUES ('" + progressPercentage + "', '" + userEmail + "', '"
                + contentItemID + "')";
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

    // update progress in database
    public static boolean updateProgress(int id, int progressPercentage) {

        // validate progress percentage
        if (ValidateFunctions.isValidPercentage(progressPercentage) == false) {
            throw new IllegalArgumentException("The percentage \"" + progressPercentage + "\" is invalid");
        }

        // set up variables
        String SQL = "UPDATE [Progress] SET progressPercentage = '" + progressPercentage + "' WHERE id = '" + id + "'";
        Connection con = getDbConnection();
        Statement stmt = null;

        try {
            // execute statement
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

    // delete progress from database
    public static boolean deleteProgress(int id) {

        // set up variables
        String SQL = "DELETE FROM [Progress] WHERE id = " + id;
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

    // get a progress with user email and content item from database
    public static final Progress getProgressWithUserAndContentItem(String userEmail, int contentItemID) {

        // set up variables
        String SQL = "SELECT * FROM Progress WHERE userEmail = '" + userEmail + "' AND contentItemID = "
                + contentItemID;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Progress data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int ID = rs.getInt("ID");
                int progressPercentage = rs.getInt("progressPercentage");

                // create progress from class
                data = new Progress(ID, progressPercentage, contentItemID);
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

    // get all progresses for a specific user
    public static final ObservableList<Progress> getProgressListWithUserEmail(String userEmail) {

        // set up variables
        String SQL = "SELECT * FROM Progress WHERE userEmail = '" + userEmail + "'";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Progress> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int ID = rs.getInt("ID");
                int progressPercentage = rs.getInt("progressPercentage");
                int contentItemID = rs.getInt("contentItemID");

                // create progress from class and add to list
                data.add(new Progress(ID, progressPercentage, contentItemID));
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