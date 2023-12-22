package com.example.database;

import static org.junit.Assert.assertTrue;

import java.sql.*;

import com.example.course.Course;
import com.example.course.Course.DifficultyLevel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

    public static Connection getDbConnection() {
        // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien
        // deze voor jou anders is.
        String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=false;encrypt=true;trustServerCertificate=true;";

        String username = "group1";

        String password = "group12345";

        // Connection beheert informatie over de connectie met de database.
        Connection con = null;

        try {
            // 'Importeer' de driver die je gedownload hebt.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Maak de verbinding met de database.
            con = DriverManager.getConnection(connectionUrl, username, password);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

}
