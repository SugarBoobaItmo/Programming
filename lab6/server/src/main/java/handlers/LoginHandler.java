package handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import database.DatabaseManager;
import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;

public class LoginHandler extends Handler{
    private String name = "login";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) {
        String login = (String) request.getData().get("login");
        String password = (String) request.getData().get("password");

        DatabaseManager databaseManager = new DatabaseManager();
    
        try {
            Connection connection = databaseManager.getConnection();
            String sqlStatement = "SELECT id, password, salt FROM users WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                String securePassword = getSecurePassword(password, resultSet.getBytes("salt"));
                if (securePassword.equals(resultSet.getString("password"))) {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("id", resultSet.getString("id"));
                    return new Response(true, "User logged in successfully", data);
                } else {
                    return new Response(false, "Wrong password", null);
                }
            } else {
                return new Response(false, "User does not exist", null);
            }

        } catch (SQLException e) {
            return new Response(false, e.getMessage(), null);
        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        }

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
    
}
