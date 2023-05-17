package handlers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import database.DatabaseManager;
import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;

public class LogupHandler extends Handler {
    private String name = "logup";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) throws Exception {
        String login = (String) request.getData().get("login");
        String password = (String) request.getData().get("password");

        DatabaseManager databaseManager = new DatabaseManager();
        Connection connection = databaseManager.getConnection();
        if (userExists(connection, login)) {
            return new Response(false, "User already exists", null);
        }
        String sqlStatement = "INSERT INTO users (login, password, salt) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

        byte[] salt = getSalt();
        String securePassword = getSecurePassword(password, salt);

        preparedStatement.setString(1, login);
        preparedStatement.setString(2, securePassword);
        preparedStatement.setBytes(3, salt);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            String sqlStatement2 = "SELECT id FROM users WHERE login = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlStatement2);
            preparedStatement2.setString(1, login);

            ResultSet id = preparedStatement2.executeQuery();
            connection.close();

            if (id.next()) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("id", id.getLong(1));
                return new Response(true, "User registered successfully", data);
            }
        }
        return new Response(false, "User registration failed", null);
    }

    private boolean userExists(Connection connection, String login) {
        String sqlStatement = "SELECT * FROM users WHERE login = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.close();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getSecurePassword(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
