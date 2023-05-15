package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    public Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        Connection connection = null;
        // System.out.println(new InputStreamReader(new FileInputStream("/src/main/java/database/db.cfg")));
        try (InputStream input = new FileInputStream("src/main/java/resources/db.cfg")) {
            properties.load(input);
        }
        try {
            Class.forName("org.postgresql.Driver");
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
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
