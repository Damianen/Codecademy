package com.example.database;

import com.example.course.ContentItem.Status;

import java.sql.*;
import java.time.LocalDate;

//most content item functions are not meant to be used on their own but rather in the module or webcast database classes
public class DatabaseContentItem extends Database {

    // create a contentItem in database
    public static boolean createContentItem(Connection con, String title, LocalDate publicationDate, Status status,
            String description) {

        // set up variables
        String SQL = "INSERT INTO [ContentItem] VALUES ('" + title + "', '" + publicationDate + "', '" + status + "', '"
                + description + "')";
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
        // close statement connection
        finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
        }

    }

    // update content item in database
    public static boolean updateContentItem(int id, String title, LocalDate publicationDate, Status status,
            String description) {

        // set up variables
        String SQL = "UPDATE [ContentItem] SET title = '" + title + "', publicationDate = '" + publicationDate
                + "', status = '" + status + "', description = '" + description + "' WHERE ID = '" + id + "'";
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

    // delete content item in database
    public static boolean deleteContentItem(int id) {

        // set up variables
        String SQL = "DELETE FROM [ContentItem] WHERE ID = '" + id + "'";
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

    // get content item type
    public static String getContentItemType(int contentItemID) {

        // set up variables
        String SQL = "SELECT CASE WHEN EXISTS (SELECT 1 FROM Webcast WHERE contentItemID = " + contentItemID
                + ") THEN 'Webcast' WHEN EXISTS (SELECT 1 FROM Module WHERE contentItemID = " + contentItemID
                + ") THEN 'Module' ELSE 'Unknown' END AS ContentType";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                data = rs.getString("ContentType");
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
