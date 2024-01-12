package com.example.database;

import com.example.course.Speaker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseSpeaker extends Database {

    // get list of all speakers from database
    public static final ObservableList<Speaker> getSpeakerList() {

        // set up variables
        String SQL = "SELECT * FROM Speaker";
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        final ObservableList<Speaker> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("name");
                String organization = rs.getString("organization");
                int id = rs.getInt("id");

                // create speaker from class and add to list
                data.add(new Speaker(id, name, organization));
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

    // read speaker from database
    public static Speaker readSpeaker(int id) {

        // set up variables
        String SQL = "SELECT * FROM [Speaker] WHERE ID = " + id;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        Speaker data = null;

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("name");
                String organization = rs.getString("organization");

                // create speaker from class
                data = new Speaker(id, name, organization);
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

    // get all speakers from database except one
    public static ObservableList<Speaker> readForNewSpeaker(int id) {

        // set up variables
        String SQL = "SELECT * FROM [Speaker] WHERE NOT ID = " + id;
        Connection con = getDbConnection();
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Speaker> data = FXCollections.observableArrayList();

        try {
            // execute query
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("name");
                String organization = rs.getString("organization");
                int newId = rs.getInt("ID");

                // create speaker from class and add to list
                data.add(new Speaker(newId, name, organization));
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

    // create speaker in database
    public static boolean createSpeaker(String speaker, String organization) {

        // set up variables
        String SQL = "INSERT INTO [Speaker] VALUES ('" + speaker + "', '" + organization + "')";
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

    // update speaker in database
    public static boolean updateSpeaker(int id, String speaker, String organization) {

        // set up variables
        String SQL = "UPDATE [Speaker] SET email = '" + speaker + "', name = '" + organization + "' WHERE id = " + id;
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

    // delete speaker from database
    public static boolean deleteSpeaker(int id) {

        // set up variables
        String SQL = "DELETE FROM [Speaker] WHERE id = " + id;
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
}
