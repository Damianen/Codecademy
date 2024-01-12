package com.example.database;

import com.example.ValidateFunctions.ValidateFunctions;
import com.example.course.ContentItem;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseWebcast extends Database{

    //read webcast from database
    public static Webcast readWebcast(int id) {

        //set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Webcast.ID AS WebcastID, Webcast.URL, Webcast.speakerID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID WHERE Webcast.ID = " + id;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Webcast data = null;

        try {
            //execute query
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

                //create webcast from class
                data = new Webcast(contentItemID, title, publicationDate, status, description, id, url, speakerID);

            }
            
            return data;

        } 
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        //close all connections
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    //get all webcasts in a list
    public static ObservableList<Webcast> readWebcastList() {

        //set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Webcast.ID AS WebcastID, Webcast.URL, Webcast.speakerID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Webcast> data = FXCollections.observableArrayList();

        try {
            //execute query
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

                //create webcast from class and add to list
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

    //read webcast from database with content item id
    public static Webcast readWebcastWithContentItemID(int contentItemID) {

        //set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Webcast.ID AS WebcastID, Webcast.URL, Webcast.speakerID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID WHERE Webcast.contentItemID = " + contentItemID;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Webcast data = null;

        try {
            //execute query
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

                //create webcast from class
                data = new Webcast(contentItemID, title, publicationDate, status, description, id, url, speakerID);

            }
            
            return data;

        } 
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        //close all connections
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }
    
    //create a webcast in the database
    public static boolean createWebcast(String title, LocalDate publicationDate, Status status, String description, String url, int speakerID) {

        //validate publication date
        if (ValidateFunctions.validateDate(publicationDate.getDayOfMonth(), publicationDate.getMonthValue(), publicationDate.getYear()) != true) {
            throw new IllegalArgumentException("The Date \"" + publicationDate + "\" is invalid");
        }

        //validate url
        try {
            ValidateFunctions.validateFormatURL(url);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        //set up variables
        Connection con = getDbConnection();
        Statement stmt = null;

        //create content item
        if(DatabaseContentItem.createContentItem(con, title, publicationDate, status, description) == true){

            //set up query
            String SQL = "INSERT INTO [Webcast] VALUES ('" + url + "', '" + speakerID + "', SCOPE_IDENTITY())";

            try {
                //execute query
                stmt = con.createStatement();
                stmt.executeUpdate(SQL);

                return true;

            } 
            // Handle any errors that may have occurred
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            //close all connections
            finally {
                if (stmt != null) try { stmt.close(); } catch(Exception e) {}
                if (con != null) try { con.close(); } catch(Exception e) {}
            }
        }else{
            return false;
        }
    }

    //update webcast in database
    public static boolean updateWebcast(int id, String title, LocalDate publicationDate, Status status, String description, String url, int speakerId) {

        //validat publication date
        if (ValidateFunctions.validateDate(publicationDate.getDayOfMonth(), publicationDate.getMonthValue(), publicationDate.getYear()) != true) {
            throw new IllegalArgumentException("The Date \"" + publicationDate + "\" is invalid");
        }

        //validate url
        try {
            ValidateFunctions.validateFormatURL(url);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        
        //get original webcast
        Webcast webcast = readWebcast(id);

        //update content item
        if(DatabaseContentItem.updateContentItem(webcast.getContentItemId(), title, publicationDate, status, description) == true){

            // set up variables
            String SQL = "UPDATE [Webcast] SET URL = '" + url + "', speakerID = '" + speakerId + "' WHERE ID = " + id;
            Connection con = getDbConnection();
            Statement stmt = null;

            try {
                //execute query
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
                if (stmt != null) try { stmt.close(); } catch(Exception e) {}
                if (con != null) try { con.close(); } catch(Exception e) {}
            }
        }else{
            return false;
        }

    }

    //delet webcast from database
    public static boolean deleteWebcast(int id) {
        
        //set up variables
        Webcast webcast = readWebcast(id);
        boolean deleted = DatabaseContentItem.deleteContentItem(webcast.getContentItemId());

        if(deleted == true){
            return true;
        }else{
            return false;
        }
    }

    //get all webcasts not seen for a specific user
    public static ObservableList<ContentItem> getNotSeenWebcastListForUser(String userEmail) {

        //set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Webcast.ID AS WebcastID, Webcast.URL, Webcast.speakerID FROM ContentItem INNER JOIN Webcast ON ContentItem.ID = Webcast.contentItemID WHERE contentItemID NOT IN (SELECT contentItemID FROM Progress WHERE userEmail = '" + userEmail + "')";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<ContentItem> data = FXCollections.observableArrayList();

        try {
            //execute query
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

                //create webcast from class and add to list
                data.add(new Webcast(contentItemID, title, publicationDate, status, description, id, url, speakerID));

            }
            
            return data;

        } 
        // Handle any errors that may have occurred
        catch (Exception e) {
            e.printStackTrace();
            return data;
        }
        //close all connections
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }
}
