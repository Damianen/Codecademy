package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    public static Connection getDbConnection() {

        // Set up your connection string
        String connectionUrl = "jdbc:sqlserver://192.168.178.174:1433;databaseName=Codecademy;integratedSecurity=false;encrypt=true;trustServerCertificate=true;";

        // Set up username
        String username = "group1";
        
        // Set up password
        String password = "group12345";

        // Set up connection variable
        Connection con = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // get the database connection
            con = DriverManager.getConnection(connectionUrl, username, password);
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    // experimental function where we create our own select query for search
    // functions
    public static String getSQLQuery(String table, HashMap<String, String> searchArgs) {
        String SQL = "SELECT * FROM " + table + " WHERE ";
        final ArrayList<String> sqlStrings = new ArrayList<String>();

        searchArgs.forEach((key, value) -> {
            sqlStrings.add(key + " LIKE '%" + value + "%' AND ");
        });

        for (int i = 0; i < sqlStrings.size(); i++) {
            SQL += sqlStrings.get(i);
        }

        SQL = SQL.substring(0, SQL.length() - 5);

        return SQL;
    }

}
