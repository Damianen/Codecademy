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

public class DatabaseWebcast extends Database{

    public static Webcast readWebcast(int id) {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Webcast.ID AS WebcastID, Webcast.URL, Webcast.speakerID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID WHERE Webcast.ID = " + id;

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Webcast data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int contentItemID = rs.getInt("ContentItemID");
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                
                String url = rs.getString("URL");
                int speakerID = rs.getInt("speakerID");

                data = new Webcast(contentItemID, title, publicationDate, status, description, id, url, speakerID);

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

    public static ObservableList<ContentItem> readWebcastList() {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Webcast.ID AS WebcastID, Webcast.URL, Webcast.speakerID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<ContentItem> data = FXCollections.observableArrayList();

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int contentItemID = rs.getInt("ContentItemID");
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                int id = rs.getInt("WebcastID");
                String url = rs.getString("URL");
                int speakerID = rs.getInt("speakerID");

                data.add(new Webcast(contentItemID, title, publicationDate, status, description, id, url, speakerID));

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

    public static Webcast readWebcastWithContentItemID(int contentItemID) {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Webcast.ID AS WebcastID, Webcast.URL, Webcast.speakerID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID WHERE Webcast.contentItemID = " + contentItemID;

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Webcast data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                
                int id = rs.getInt("WebcastID");
                String url = rs.getString("URL");
                int speakerID = rs.getInt("speakerID");

                data = new Webcast(contentItemID, title, publicationDate, status, description, id, url, speakerID);

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
    
    public static boolean createWebcast(String title, LocalDate publicationDate, Status status, String description, String url, int speakerID) {

        Connection con = getDbConnection();

        if(DatabaseContentItem.createContentItem(con, title, publicationDate, status, description) == true){

            String SQL = "INSERT INTO [Webcast] VALUES ('" + url + "', '" + speakerID + "', SCOPE_IDENTITY())";

            

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
        }else{
            return false;
        }

    }

    public static boolean updateWebcast(int id, String title, LocalDate publicationDate, Status status, String description, String url, int speakerId) {

        Webcast webcast = readWebcast(id);

        if(DatabaseContentItem.updateContentItem(webcast.getContentItemId(), title, publicationDate, status, description) == true){

            String SQL = "UPDATE [Webcast] SET URL = '" + url + "', speakerID = '" + speakerId + "' WHERE ID = " + id;

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
        }else{
            return false;
        }

    }

    public static boolean deleteWebcast(int id) {
        
        Webcast webcast = readWebcast(id);

        boolean deleted = DatabaseContentItem.deleteContentItem(webcast.getContentItemId());

        if(deleted == true){
            return true;
        }else{
            return false;
        }
    }

}
