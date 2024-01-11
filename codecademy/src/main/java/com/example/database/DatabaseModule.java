package com.example.database;

import com.example.ValidateFunctions;
import com.example.course.ContentItem;
import com.example.course.Module;
import com.example.course.ContentItem.Status;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.text.AbstractDocument.Content;

public class DatabaseModule extends Database{

    public static Module readModule(int id) {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE Module.ID = " + id;

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
                
                double version = rs.getDouble("version");
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

    public static String readModuleCourseTitle(int id) {

        String SQL = "SELECT courseTitle, ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE Module.ID = " + id;

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        String data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                data = rs.getString("courseTitle");

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

    public static ObservableList<Module> readModuleList() {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Module> data = FXCollections.observableArrayList();

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                
                int contentItemID = rs.getInt("ContentItemID");
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                int id = rs.getInt("ModuleID");
                double version = rs.getDouble("version");
                int orderNumber = rs.getInt("orderNumber");
                String emailContactPerson = rs.getString("emailContactPerson");

                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version, emailContactPerson, orderNumber));

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

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN Module ON ContentItem.ID = Module.contentItemID WHERE ContentItem.ID = " + contentItemID;

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
                double version = rs.getDouble("version");
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

    public static boolean createModule(String title, LocalDate publicationDate, Status status, String description, double version, int orderNumber, String emailContactPerson) {

        if (ValidateFunctions.validateDate(publicationDate.getDayOfMonth(), publicationDate.getMonthValue(), publicationDate.getYear()) != true) {
                throw new IllegalArgumentException("The Date \"" + publicationDate + "\" is invalid");
        }

        Connection con = getDbConnection();

        if(DatabaseContentItem.createContentItem(con, title, publicationDate, status, description) == true){

            String SQL = "INSERT INTO [Module] VALUES ('" + version + "', '" + orderNumber + "', '" + emailContactPerson + "', SCOPE_IDENTITY(), null)";

            if(orderNumber == 0){
                SQL = "INSERT INTO [Module] VALUES ('" + version + "', " + null + ", '" + emailContactPerson + "', SCOPE_IDENTITY(), null)";
            }

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

    public static boolean updateModule(int id, String title, LocalDate publicationDate, Status status, String description, double version, int orderNumber, String emailContactPerson, String courseTitle) {

        if (ValidateFunctions.validateDate(publicationDate.getDayOfMonth(), publicationDate.getMonthValue(), publicationDate.getYear()) != true) {
                throw new IllegalArgumentException("The Date \"" + publicationDate + "\" is invalid");
        }

        Module module = readModule(id);

        if (courseTitle != null) {
            int orderNumberMax = getCourseModules(courseTitle).size();

            if(orderNumber > orderNumberMax){
                throw new IllegalArgumentException("Order number may not exceed the number of modules in this course (" + orderNumberMax + ")");
            }
        }
        
        if(DatabaseContentItem.updateContentItem(module.getContentItemId(), title, publicationDate, status, description) == true){

            String SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = '" + orderNumber + "', emailContactPerson = '" + emailContactPerson + "', courseTitle = '" + courseTitle + "' WHERE ID = " + id;

            if(orderNumber == 0 && courseTitle != null){
                SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = " + null + ", emailContactPerson = '" + emailContactPerson + "', courseTitle = '" + courseTitle + "' WHERE ID = " + id;
            }else if(orderNumber != 0 && courseTitle == null){
                SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = '" + orderNumber + "', emailContactPerson = '" + emailContactPerson + "', courseTitle = " + null + " WHERE ID = " + id;
            }else if(orderNumber == 0 && courseTitle == null){
                SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = " + null + ", emailContactPerson = '" + emailContactPerson + "', courseTitle = " + null + " WHERE ID = " + id;
            }

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

    public static boolean deleteModule(int id) {
        
        Module module = readModule(id);

        boolean deleted = DatabaseContentItem.deleteContentItem(module.getContentItemId());

        if(deleted == true){
            return true;
        }else{
            return false;
        }
    }

    public static ObservableList<ContentItem> getCourseModules(String courseTitle) {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE courseTitle = '" + courseTitle + "' ORDER BY orderNumber ASC";

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
                int id = rs.getInt("ModuleID");
                double version = rs.getDouble("version");
                int orderNumber = rs.getInt("orderNumber");
                String emailContactPerson = rs.getString("emailContactPerson");

                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version, emailContactPerson, orderNumber));

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

    public static boolean updateOrderNumbersInCourse(int moduleID, int newOrderNumber, String courseTitle) {

        int oldOrderNumber = readModule(moduleID).getOrderNumber();

        int orderNumberMax = getCourseModules(courseTitle).size();  

        if(newOrderNumber < 1){
            throw new IllegalArgumentException("Order number cannot be lower than 1");
        }

        if(oldOrderNumber > orderNumberMax){
            throw new IllegalArgumentException("Order number may not exceed the number of modules in this course (" + orderNumberMax + ")");
        }
 
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM Module INNER JOIN ContentItem ON ContentItem.ID = Module.contentItemID WHERE orderNumber >= " + newOrderNumber + " AND orderNumber < " + oldOrderNumber + " AND courseTitle = '" + courseTitle + "' ORDER BY orderNumber DESC";
        
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Module> data = FXCollections.observableArrayList();
        

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int contentItemID = rs.getInt("contentItemID");
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                
                int id = rs.getInt("ModuleID");
                double version = rs.getDouble("version");
                int orderNumberDB = rs.getInt("orderNumber");
                String emailContactPerson = rs.getString("emailContactPerson");

                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version, emailContactPerson, orderNumberDB));

            }

            Module resetOrderNumberModule = readModule(moduleID);

            if(updateModule(resetOrderNumberModule.getId(), resetOrderNumberModule.getTitle(), resetOrderNumberModule.getPublicationDate(), resetOrderNumberModule.getStatus(), resetOrderNumberModule.getDescription(), resetOrderNumberModule.getVersion(), 0, resetOrderNumberModule.getContactPerson().getEmail(), courseTitle) == false){
                return false;
            }

            for (Module module : data) {
                updateModule(module.getId(), module.getTitle(), module.getPublicationDate(), module.getStatus(), module.getDescription(), module.getVersion(), module.getOrderNumber() + 1, module.getContactPerson().getEmail(), courseTitle);
            }

            if(updateModule(resetOrderNumberModule.getId(), resetOrderNumberModule.getTitle(), resetOrderNumberModule.getPublicationDate(), resetOrderNumberModule.getStatus(), resetOrderNumberModule.getDescription(), resetOrderNumberModule.getVersion(), newOrderNumber, resetOrderNumberModule.getContactPerson().getEmail(), courseTitle) == false){
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    public static boolean updateOrderNumbersRemoveFromCourse(int moduleID, int oldOrderNumber, String courseTitle) {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM Module INNER JOIN ContentItem ON ContentItem.ID = Module.contentItemID WHERE orderNumber > " + oldOrderNumber + " AND courseTitle = '" + courseTitle + "' ORDER BY orderNumber ASC";
        
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Module> data = FXCollections.observableArrayList();
        

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int contentItemID = rs.getInt("contentItemID");
                String title = rs.getString("title");
                LocalDate publicationDate = rs.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(rs.getString("status"));
                String description = rs.getString("description");
                
                int id = rs.getInt("ModuleID");
                double version = rs.getDouble("version");
                int orderNumberDB = rs.getInt("orderNumber");
                String emailContactPerson = rs.getString("emailContactPerson");

                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version, emailContactPerson, orderNumberDB));

            }

            Module resetOrderNumberModule = readModule(moduleID);

            if(updateModule(resetOrderNumberModule.getId(), resetOrderNumberModule.getTitle(), resetOrderNumberModule.getPublicationDate(), resetOrderNumberModule.getStatus(), resetOrderNumberModule.getDescription(), resetOrderNumberModule.getVersion(), 0, resetOrderNumberModule.getContactPerson().getEmail(), null) == false){
                return false;
            }

            for (Module module : data) {
                updateModule(module.getId(), module.getTitle(), module.getPublicationDate(), module.getStatus(), module.getDescription(), module.getVersion(), module.getOrderNumber() - 1, module.getContactPerson().getEmail(), courseTitle);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    public static ObservableList<ContentItem> getModuleWithNoCourseList() {

        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE courseTitle IS NULL";

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
                int id = rs.getInt("ModuleID");
                double version = rs.getDouble("version");
                int orderNumber = rs.getInt("orderNumber");
                String emailContactPerson = rs.getString("emailContactPerson");

                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version, emailContactPerson, orderNumber));

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

    public static boolean addModuleToCourse(int id, int orderNumber, String courseTitle) {

        String SQL = "UPDATE [Module] SET orderNumber = " + orderNumber + ", courseTitle = '" + courseTitle + "' WHERE ID = " + id;

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
