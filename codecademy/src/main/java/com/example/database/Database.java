package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    public static Connection getDbConnection() {
        // Dit zijn de instellingen voor de verbinding. Vervang de databaseName indien
        // deze voor jou anders is.
        String connectionUrl = "jdbc:sqlserver://Localhost;databaseName=Codecademy;integratedSecurity=false;encrypt=true;trustServerCertificate=true;";

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
