package handlers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import fqme.connection.ConnectionManager;
import fqme.models.UsersModel;
import fqme.view.View;

/**
 * 
 * The LoginHandler class handles user login functionality.
 * It checks the provided login credentials against the database
 * and generates a secure password using SHA-384 algorithm.
 */
public class LoginHandler extends Handler {
    private String name = "login";

    /**
     * Retrieves the name of the LoginHandler.
     *
     * @return The name of the LoginHandler.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Handles the login request by checking the provided login
     * credentials against the database and returning the appropriate response.
     *
     * @param request The login request containing the login and password.
     * @param userId  The ID of the user making the request.
     * @return The response indicating the result of the login operation.
     * @throws Exception If an error occurs during the login process.
     */
    @Override
    public Response handle(Request request, String userId) throws Exception {
        String login = (String) request.getData().get("login");
        String password = (String) request.getData().get("password");

        try (Connection connection = ConnectionManager.getConnection(UsersModel.class)) {
            View<UsersModel> usersView = View.of(UsersModel.class, connection);

            UsersModel users = (UsersModel) usersView.get(UsersModel.login_.eq(login)).iterator().next();
            if (users != null) {
                String securePassword = getSecurePassword(password, users.getSalt());
                if (securePassword.equals(users.getPassword())) {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("id", users.getId());
                    return new Response(true, "User logged in successfully", data);
                } else {
                    return new Response(false, "Wrong password", null);
                }
            } else {
                return new Response(false, "User does not exist", null);
            }
        }
    }

    /**
     * Generates a secure password using SHA-384 algorithm.
     *
     * @param password The original password to be secured.
     * @param salt     The salt to be used for password hashing.
     * @return The secure password generated using SHA-384 algorithm.
     */
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
