package com.example.database;

import com.example.ValidateFunctions.ValidateFunctions;
import com.example.course.ContentItem;
import com.example.course.Module;
import com.example.course.ContentItem.Status;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseModule extends Database {

    // read module from database
    public static Module readModule(int id) {

        // set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE Module.ID = "
                + id;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Module data = null;

        try {
            // execute query
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

                // create module from class
                data = new Module(contentItemID, title, publicationDate, status, description, id, version,
                        emailContactPerson, orderNumber);

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

    // get course title from a module
    public static String readModuleCourseTitle(int id) {

        // set up variables
        String SQL = "SELECT courseTitle, ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE Module.ID = "
                + id;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                data = rs.getString("courseTitle");

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

    // get module list from database
    public static ObservableList<Module> readModuleList() {

        // set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Module> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // create new module from class and add to list
                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version,
                        emailContactPerson, orderNumber));

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

    // read module from database wit content item id
    public static Module readModuleWithContentItemID(int contentItemID) {

        // set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN Module ON ContentItem.ID = Module.contentItemID WHERE ContentItem.ID = "
                + contentItemID;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Module data = null;

        try {

            // execute query
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

                // create ne wmodule from class
                data = new Module(contentItemID, title, publicationDate, status, description, id, version,
                        emailContactPerson, orderNumber);

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

    // create module in database
    public static boolean createModule(String title, LocalDate publicationDate, Status status, String description,
            double version, int orderNumber, String emailContactPerson) {

        // validate publications date
        if (ValidateFunctions.validateDate(publicationDate.getDayOfMonth(), publicationDate.getMonthValue(),
                publicationDate.getYear()) != true) {
            throw new IllegalArgumentException("The Date \"" + publicationDate + "\" is invalid");
        }

        // set variables
        Connection con = getDbConnection();
        Statement stmt = null;

        // create content item
        if (DatabaseContentItem.createContentItem(con, title, publicationDate, status, description) == true) {

            // set up query
            String SQL = "INSERT INTO [Module] VALUES ('" + version + "', '" + orderNumber + "', '" + emailContactPerson
                    + "', SCOPE_IDENTITY(), null)";

            // check if ordernumber is null
            if (orderNumber == 0) {
                // update query
                SQL = "INSERT INTO [Module] VALUES ('" + version + "', " + null + ", '" + emailContactPerson
                        + "', SCOPE_IDENTITY(), null)";
            }

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
        } else {
            return false;
        }

    }

    // update module in database
    public static boolean updateModule(int id, String title, LocalDate publicationDate, Status status,
            String description, double version, int orderNumber, String emailContactPerson, String courseTitle) {

        // validate publication date
        if (ValidateFunctions.validateDate(publicationDate.getDayOfMonth(), publicationDate.getMonthValue(),
                publicationDate.getYear()) != true) {
            throw new IllegalArgumentException("The Date \"" + publicationDate + "\" is invalid");
        }

        // read module
        Module module = readModule(id);

        // check if course title is empty
        if (courseTitle != null) {
            // get module count in course
            int orderNumberMax = getCourseModules(courseTitle).size();

            // check if ordernumber is bigger then module count
            if (orderNumber > orderNumberMax) {
                throw new IllegalArgumentException(
                        "Order number may not exceed the number of modules in this course (" + orderNumberMax + ")");
            }
        }

        // update content item
        if (DatabaseContentItem.updateContentItem(module.getContentItemId(), title, publicationDate, status,
                description) == true) {

            // set up query
            String SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = '" + orderNumber
                    + "', emailContactPerson = '" + emailContactPerson + "', courseTitle = '" + courseTitle
                    + "' WHERE ID = " + id;

            // update query based no variables
            if (orderNumber == 0 && courseTitle != null) {
                SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = " + null
                        + ", emailContactPerson = '" + emailContactPerson + "', courseTitle = '" + courseTitle
                        + "' WHERE ID = " + id;
            } else if (orderNumber != 0 && courseTitle == null) {
                SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = '" + orderNumber
                        + "', emailContactPerson = '" + emailContactPerson + "', courseTitle = " + null + " WHERE ID = "
                        + id;
            } else if (orderNumber == 0 && courseTitle == null) {
                SQL = "UPDATE [Module] SET version = '" + version + "', orderNumber = " + null
                        + ", emailContactPerson = '" + emailContactPerson + "', courseTitle = " + null + " WHERE ID = "
                        + id;
            }

            // set up variables
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
        } else {
            return false;
        }
    }

    // delete module in database
    public static boolean deleteModule(int id) {

        // read module
        Module module = readModule(id);

        // delete content item (module will be deleted throu cascade)
        boolean deleted = DatabaseContentItem.deleteContentItem(module.getContentItemId());

        if (deleted == true) {
            return true;
        } else {
            return false;
        }
    }

    // get module list
    public static ObservableList<ContentItem> getCourseModules(String courseTitle) {

        // set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE courseTitle = '"
                + courseTitle + "' ORDER BY orderNumber ASC";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<ContentItem> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // create module from class and add to list
                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version,
                        emailContactPerson, orderNumber));

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

    // update order number in a module thats linked to a course
    public static boolean updateOrderNumbersInCourse(int moduleID, int newOrderNumber, String courseTitle) {

        // get old order number and max order number
        int oldOrderNumber = readModule(moduleID).getOrderNumber();
        int orderNumberMax = getCourseModules(courseTitle).size();

        if(oldOrderNumber == newOrderNumber){
            throw new IllegalArgumentException("old order number cannot be the same a new order number");
        }

        // check if ordernumber is smaller than 1
        if (newOrderNumber < 1) {
            throw new IllegalArgumentException("Order number cannot be lower than 1");
        }

        // check if new order number is bigger than max order number
        if (newOrderNumber > orderNumberMax) {
            throw new IllegalArgumentException(
                    "Order number may not exceed the number of modules in this course (" + orderNumberMax + ")");
        }

        // set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM Module INNER JOIN ContentItem ON ContentItem.ID = Module.contentItemID WHERE orderNumber >= "
                + newOrderNumber + " AND orderNumber < " + oldOrderNumber + " AND courseTitle = '" + courseTitle
                + "' ORDER BY orderNumber DESC";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Module> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // create new module from class and add to list
                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version,
                        emailContactPerson, orderNumberDB));

            }

            // read original module
            Module resetOrderNumberModule = readModule(moduleID);

            // reset original module order number to null
            if (updateModule(resetOrderNumberModule.getId(), resetOrderNumberModule.getTitle(),
                    resetOrderNumberModule.getPublicationDate(), resetOrderNumberModule.getStatus(),
                    resetOrderNumberModule.getDescription(), resetOrderNumberModule.getVersion(), 0,
                    resetOrderNumberModule.getContactPerson().getEmail(), courseTitle) == false) {
                return false;
            }

            // update all other module order numbers
            for (Module module : data) {
                updateModule(module.getId(), module.getTitle(), module.getPublicationDate(), module.getStatus(),
                        module.getDescription(), module.getVersion(), module.getOrderNumber() + 1,
                        module.getContactPerson().getEmail(), courseTitle);
            }

            // set original order module new order number
            if (updateModule(resetOrderNumberModule.getId(), resetOrderNumberModule.getTitle(),
                    resetOrderNumberModule.getPublicationDate(), resetOrderNumberModule.getStatus(),
                    resetOrderNumberModule.getDescription(), resetOrderNumberModule.getVersion(), newOrderNumber,
                    resetOrderNumberModule.getContactPerson().getEmail(), courseTitle) == false) {
                return false;
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

    // update order numbers when you delete a module from course
    public static boolean updateOrderNumbersRemoveFromCourse(int moduleID, int oldOrderNumber, String courseTitle) {

        // set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM Module INNER JOIN ContentItem ON ContentItem.ID = Module.contentItemID WHERE orderNumber > "
                + oldOrderNumber + " AND courseTitle = '" + courseTitle + "' ORDER BY orderNumber ASC";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Module> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // create module from class and add to list
                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version,
                        emailContactPerson, orderNumberDB));

            }

            // read original module
            Module resetOrderNumberModule = readModule(moduleID);

            // resest ordernumber and course title to null
            if (updateModule(resetOrderNumberModule.getId(), resetOrderNumberModule.getTitle(),
                    resetOrderNumberModule.getPublicationDate(), resetOrderNumberModule.getStatus(),
                    resetOrderNumberModule.getDescription(), resetOrderNumberModule.getVersion(), 0,
                    resetOrderNumberModule.getContactPerson().getEmail(), null) == false) {
                return false;
            }

            // update all other modules order numbers
            for (Module module : data) {
                updateModule(module.getId(), module.getTitle(), module.getPublicationDate(), module.getStatus(),
                        module.getDescription(), module.getVersion(), module.getOrderNumber() - 1,
                        module.getContactPerson().getEmail(), courseTitle);
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

    // get list of modules without a course
    public static ObservableList<ContentItem> getModuleWithNoCourseList() {

        // set up variables
        String SQL = "SELECT ContentItem.ID AS ContentItemID, ContentItem.title, ContentItem.publicationDate, ContentItem.status, ContentItem.description, Module.ID AS ModuleID, Module.version, Module.orderNumber, Module.emailContactPerson FROM ContentItem INNER JOIN [Module] ON ContentItem.ID = Module.contentItemID WHERE courseTitle IS NULL";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<ContentItem> data = FXCollections.observableArrayList();

        try {
            // execute query
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

                // add module from class and add to list
                data.add(new Module(contentItemID, title, publicationDate, status, description, id, version,
                        emailContactPerson, orderNumber));

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

    // add module to course
    public static boolean addModuleToCourse(int id, int orderNumber, String courseTitle) {

        // set up variables
        String SQL = "UPDATE [Module] SET orderNumber = " + orderNumber + ", courseTitle = '" + courseTitle
                + "' WHERE ID = " + id;
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
}
