package com.example.database;

import com.example.course.ContentItem;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;
import com.example.exeptions.AlreadyExistsException;

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

}
