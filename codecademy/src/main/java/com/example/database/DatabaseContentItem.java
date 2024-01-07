package com.example.database;

import com.example.ValidateFunctions;
import com.example.course.ContentItem;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;
import com.example.exeptions.AlreadyExistsException;
import com.example.user.User;
import com.example.user.User.Gender;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

import javax.swing.text.AbstractDocument.Content;

public class DatabaseContentItem extends Database{
    
    public static boolean createContentItem(Connection con, String title, LocalDate publicationDate, Status status, String description) {

        String SQL = "INSERT INTO [ContentItem] VALUES ('" + title + "', '" + publicationDate + "', '" + status + "', '" + description + "')";

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
        }

    }

    public static boolean updateContentItem(int id, String title, LocalDate publicationDate, Status status, String description) {

        String SQL = "UPDATE [ContentItem] SET title = '" + title + "', publicationDate = '" + publicationDate + "', status = '" + status + "', description = '" + description + "' WHERE ID = '" + id + "'";

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

    public static boolean deleteContentItem(int id) {
        
        String SQL = "DELETE FROM [ContentItem] WHERE ID = '" + id + "'";

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

    public static String getContentItemType(int contentItemID) {
        
        String SQL = "SELECT CASE WHEN EXISTS (SELECT 1 FROM Webcast WHERE contentItemID = " + contentItemID + ") THEN 'Webcast' WHEN EXISTS (SELECT 1 FROM Module WHERE contentItemID = " + contentItemID + ") THEN 'Module' ELSE 'Unknown' END AS ContentType";

        Connection con = getDbConnection();

        Statement stmt = null;

        ResultSet rs = null;

        String data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                data = rs.getString("ContentType");
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }

    }

}
