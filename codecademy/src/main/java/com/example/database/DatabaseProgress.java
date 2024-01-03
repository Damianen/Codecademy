package com.example.database;

import com.example.user.Enrollment;
import com.example.user.Progress;
import com.example.user.User;
import com.example.user.User.Gender;
import com.example.exeptions.AlreadyExistsException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseProgress extends Database{

    public static Progress readProgress(int id) {

        String SQL = "SELECT * FROM [Progress] WHERE id = " + id;

        Connection con = getDbConnection();

        Statement stmt = null;
        ResultSet rs = null;
        Progress data = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                int progressPercentage = rs.getInt("progressPercentage");
                int contentItemID = rs.getInt("contentItemID");
                
                data = new Progress(id, progressPercentage, contentItemID);
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
    
    public static boolean createProgress(int progressPercentage, String userEmail, int contentItemID) {

        String SQL = "INSERT INTO [Progress] VALUES ('" + progressPercentage + "', '" + userEmail + "', '" + contentItemID + "')";

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

    public static boolean updateProgress(int id, int progressPercentage) {

        String SQL = "UPDATE [Progress] SET progressPercentage = '" + progressPercentage + "' WHERE id = '" + id + "'";

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

    public static boolean deleteProgress(int id) {
        
        String SQL = "DELETE FROM [Progress] WHERE id = " + id;

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