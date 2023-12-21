package com.example;

import static com.example.course.Course.DifficultyLevel.ADVANCED;
import static com.example.course.Course.DifficultyLevel.BEGINNER;

import java.sql.*;

import com.example.course.Course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

    public static ResultSet runSQL(String SQL) {
        // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien
        // deze voor jou anders is.
        String connectionUrl = "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=false;encrypt=true;trustServerCertificate=true;";

        String username = "group1";

        String password = "group12345";

        // Connection beheert informatie over de connectie met de database.
        Connection con = null;

        // Statement zorgt dat we een SQL query kunnen uitvoeren.
        Statement stmt = null;

        // ResultSet is de tabel die we van de database terugkrijgen.
        // We kunnen door de rows heen stappen en iedere kolom lezen.
        ResultSet rs = null;

        try {
            // 'Importeer' de driver die je gedownload hebt.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Maak de verbinding met de database.
            con = DriverManager.getConnection(connectionUrl, username, password);

            stmt = con.createStatement();
            // Voer de query uit op de database.
            rs = stmt.executeQuery(SQL);

            return rs;

        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            return rs;
        }
    }

    public static void createCourse(String[] args) {

        String SQL = "SELECT * FROM Course";
        ResultSet rs = runSQL(SQL);

        try {
            while (rs.next()) {
                // Vraag per row de kolommen in die row op.
                String name = rs.getString("name");
                String subject = rs.getString("subject");
                String introText = rs.getString("introText");
                int level = rs.getInt("level");

                // Print de kolomwaarden.
                System.out.println(name + " " + subject + " " + introText + " " + level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static public final ObservableList<Course> getCourseList(String name, String subject, String introText,
            String difficultyLevel, String userName, String ModuleTitle) {
        if (difficultyLevel != null) {
            difficultyLevel.toLowerCase();
        } 
        final ObservableList<Course> data = FXCollections.observableArrayList(
                new Course("Java", "Java", "welcome to java", BEGINNER),
                new Course("Python", "Python", "welcome to Python", BEGINNER),
                new Course("c", "c", "welcome to c", BEGINNER),
                new Course("c++", "c++", "welcome to C++", ADVANCED));

        return data;
    }
}
