package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    public Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        Connection connection = null;
        // System.out.println(new InputStreamReader(new FileInputStream("/src/main/java/database/db.cfg")));
        // try (InputStream input = new FileInputStream("src/main/resources/db.cfg")) {
        //     properties.load(input);
        // }
        // try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.cfg")) {
        //     properties.load(input);
        // }
        try (InputStreamReader reader = new InputStreamReader(
            DatabaseManager.class.getClassLoader().getResourceAsStream("db.cfg"))) {
            properties.load(reader);}
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/lab6";
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            // System.out.println(url+username+password);
            connection = DriverManager.getConnection(url, username, password);
            // System.out.println("Connected to the PostgreSQL server successfully.");
            // if (connection != null) {
            //     System.out.println("Connected to the database");
            // } else {
            //     System.out.println("Failed to connect to the database");
            // }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return connection;
    }



}
