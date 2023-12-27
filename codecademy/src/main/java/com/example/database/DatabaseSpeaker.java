package com.example.database;

import com.example.user.Enrollment;
import com.example.user.User;
import com.example.course.Speaker;
import com.example.user.User.Gender;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseSpeaker extends Database{

    public static Speaker readSpeaker(int id) {

        String SQL = "SELECT * FROM [Speaker] WHERE ID = " + id;

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Speaker data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("name");
                String organization = rs.getString("organization");

                data = new Speaker(id, name, organization);
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
    
    public static boolean createSpeaker(String speaker, String organization) {

        String SQL = "INSERT INTO [Speaker] VALUES ('" + speaker + "', '" + organization + "')";

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

    public static boolean updateSpeaker(int id, String speaker, String organization) {

        String SQL = "UPDATE [Speaker] SET email = '" + speaker + "', name = '" + organization + "' WHERE id = " + id;

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

    public static boolean deleteSpeaker(int id) {
        
        String SQL = "DELETE FROM [Speaker] WHERE id = " + id;

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

    public static ArrayList<Speaker> getSpeakerList() {

        String SQL = "SELECT * FROM [Speaker]";

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;

        ArrayList<Speaker> data = new ArrayList<Speaker>();

        try{

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String organization = rs.getString("organization");

                data.add(new Speaker(id, name, organization));
            }

            return data;

        }catch(Exception e){
            e.printStackTrace();
            return data;
        }finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }
}
