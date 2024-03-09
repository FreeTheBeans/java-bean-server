package com.freethebeans.server.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.amazonaws.secretsmanager.caching.SecretCache;
import org.json.JSONObject;

public class DBConnection {
    String secretID = "dev/database/credentials";

    String username = "";
    String password = "";
    String host = "";
    int port = 0;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public DBConnection() {
        JSONObject dbSecret = getDBCredentials(secretID);
        username = dbSecret.getString("username");
        password = dbSecret.getString("password");
        host = dbSecret.getString("host");
        port = dbSecret.getInt("port");

        host = String.format("jdbc:postgresql://%s:%d/dev", host, port);

        JSONObject object = queryDB("Select * from players");
        System.out.println(object.toString());
    }

    private JSONObject getDBCredentials(String secretName) {
        try (SecretCache cache = new SecretCache()) {
            JSONObject dbSecret = new JSONObject(cache.getSecretString(secretID));
            String expectedFields[] = { "username", "password", "host", "port" };
            for (String key : expectedFields) {
                if (!dbSecret.has(key)) {
                    System.err.println("Error: expected DB credentials not found.");
                    return null;
                }
            }
            return dbSecret;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void getDummyRecord() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(host, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM players");

            while (resultSet.next()) {
                int column1Data = resultSet.getInt("player_id");
                String column2Data = resultSet.getString("username");
                System.out.println("User ID: " + column1Data + ", Username: " + column2Data);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Could not find the PostgreSQL JDBC driver. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources.");
                e.printStackTrace();
            }
        }
    }

    private JSONObject queryDB(String query) {
        JSONObject result = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(host, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            String stringResult = "{ \"results\": [";
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                stringResult += "{";
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    stringResult += String.format("\"%s\": \"%s\",", columnName, columnValue);
                }
                // Remove the trailing comma for the last key-value pair
                if (stringResult.endsWith(",")) {
                    stringResult = stringResult.substring(0, stringResult.length() - 1);
                }
                stringResult += "},";
            }

            // Remove the trailing comma if there are results
            if (stringResult.endsWith(",")) {
                stringResult = stringResult.substring(0, stringResult.length() - 1);
            }
            stringResult += "]}";
            result = new JSONObject(stringResult);
        } catch (ClassNotFoundException e) {
            System.err.println("Could not find the PostgreSQL JDBC driver. Include it in your library path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources.");
                e.printStackTrace();
            }
        }
        return result;
    }
}