package handlers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.connection.ConnectionManager;
import fqme.models.UsersModel;
import fqme.view.View;
import handlers.exceptions.ServerStorageException;

/**
 * 
 * The LogupHandler class handles user registration functionality.
 * It checks if the user already exists in the database, generates
 * a secure password using SHA-384 algorithm, and registers the user.
 */
public class LogupHandler extends Handler {
    private String name = "logup";

    /**
     * Retrieves the name of the LogupHandler.
     *
     * @return The name of the LogupHandler.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Handles the registration request by checking if the user already exists,
     * generating a secure password, and registering the user in the database.
     *
     * @param request The registration request containing the login and password.
     * @param userId  The ID of the user making the request.
     * @return The response indicating the result of the registration operation.
     * @throws Exception If an error occurs during the registration process.
     */
    @Override
    public Response handle(Request request, String userId) throws Exception {
        String login = (String) request.getData().get("login");
        String password = (String) request.getData().get("password");

        try (Connection connection = ConnectionManager.getConnection(UsersModel.class)) {

            View<UsersModel> usersView = View.of(UsersModel.class, connection);

            if (userExists(usersView, login)) {
                return new Response(false, "User already exists", null);
            }

            byte[] salt = getSalt();
            String securePassword = getSecurePassword(password, salt);

            UsersModel user = new UsersModel(login, securePassword, salt);
            usersView.put(user);

            // Neccessary to get the id of the inserted user
            UsersModel insertedUser = (UsersModel) usersView.get(UsersModel.login_.eq(login)).iterator().next();
            Integer id = insertedUser.getId();
            if (id != null) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("id", id);
                return new Response(true, "User registered successfully", data);
            }
            return new Response(false, "User registration failed", null);
        }

    }

    /**
     * Checks if the user already exists in the database.
     *
     * @param users The view representing the users in the database.
     * @param login The login of the user to check.
     * @return true if the user exists, false otherwise.
     * @throws ServerStorageException If an error occurs during the database
     *                                operation.
     */
    private boolean userExists(View<UsersModel> users, String login) throws ServerStorageException {

        try {
            View<UsersModel> usersView = users;
            Set<UsersModel> userSet = usersView.get(UsersModel.login_.eq(login));
            if (userSet.size() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new ServerStorageException("Error while checking if user exists", e);
        } catch (UnsupportedSqlType e) {
            throw new ServerStorageException("Error while checking if user exists", e);
        } catch (UnsupportedValueType e) {
            throw new ServerStorageException("Error while checking if user exists", e);
        }
        return false;

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

    /**
     * Generates a random salt.
     *
     * @return The generated salt.
     * @throws NoSuchAlgorithmException If the algorithm for generating the salt is
     *                                  not available.
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
