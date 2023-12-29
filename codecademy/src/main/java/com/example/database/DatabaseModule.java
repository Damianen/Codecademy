package com.example.database;

import com.example.course.ContentItem;
import com.example.course.Module;
import com.example.course.Webcast;
import com.example.course.ContentItem.Status;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

import javax.swing.text.AbstractDocument.Content;

public class DatabaseModule extends Database{

    public static Module readModule(int id) {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module emailContactPersion FROM ContentItem INNER JOIN Module ON ContentItem.ID = Module.contentItemID WHERE Module.ID = " + id;

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Module data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int contentItemID = rs.getInt("ContentItemID");
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                
                int version = rs.getInt("version");
                int orderNumber = rs.getInt("orderNumber");
                String emailContactPerson = rs.getString("emailContactPerson");

                data = new Module(contentItemID, title, publicationDate, status, description, id, version, emailContactPerson, orderNumber);

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

    public static Module readModuleWithContentItemID(int contentItemID) {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module emailContactPersion FROM ContentItem INNER JOIN Module ON ContentItem.ID = Module.contentItemID WHERE ContentItem.ID = " + contentItemID;

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Module data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                
                int id = rs.getInt("ModuleID");
                int version = rs.getInt("version");
                int orderNumber = rs.getInt("orderNumber");
                String emailContactPerson = rs.getString("emailContactPerson");

                data = new Module(contentItemID, title, publicationDate, status, description, id, version, emailContactPerson, orderNumber);

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
    
    public static boolean createModule(String title, LocalDate publicationDate, Status status, String description, String version, int orderNumber, String emailContactPerson, String courseTitle) {

        Connection con = getDbConnection();

        if(DatabaseContentItem.createContentItem(con, title, publicationDate, status, description) == true){

            String SQL = "INSERT INTO [Module] VALUES ('" + version + "', '" + orderNumber + "', '" + emailContactPerson + "', SCOPE_IDENTITY(), '" + courseTitle + "')";

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

    public static boolean updateModule(int id, String title, LocalDate publicationDate, Status status, String description, String version, int orderNumber, String emailContactPerson, String courseTitle) {

        Module module = readModule(id);

        if(DatabaseContentItem.updateContentItem(module.getContentItemId(), title, publicationDate, status, description) == true){

            String SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = '" + orderNumber + "', emailContactPerson = '" + emailContactPerson + "', courseTitle = '" + courseTitle + "' WHERE ID = " + id;

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
        
        Module module = readModule(id);

        boolean deleted = DatabaseContentItem.deleteContentItem(module.getContentItemId());

        if(deleted == true){
            return true;
        }else{
            return false;
        }
    }

}
