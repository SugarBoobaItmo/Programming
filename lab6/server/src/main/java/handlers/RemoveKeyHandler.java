package handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import database.DatabaseManager;
import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;

/**
 * Handler for the "remove_key" command.
 * Removes an element from the collection by its key.
 */
public class RemoveKeyHandler extends Handler {
    private String name = "remove_key";

    /**
     * @return The name of the command.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Removes an element from the collection by its key.
     * 
     * @param request The request to handle.
     * @param userId  The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) {

        CollectionRecord collectionRecord = new CollectionRecord();

        try {

            String key = (String) request.getData().get("argument");
            DatabaseManager dbManager = new DatabaseManager();
            Connection connection = dbManager.getConnection();
            String sql = "DELETE FROM groups WHERE owner_id = ? AND key = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, key);
            // statement.executeUpdate();
            int rows = statement.executeUpdate();
            collectionRecord.setCollection(CollectionStorage.load(userId.toString()).getCollection());
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);

            if (rows == 0) {
                return new Response(false, "You haven't got group with key = '"+key+"'", data);
            } else {
                return new Response(true, "StudyGroup removed successfully", data);
            }

        } catch (SQLException e) {
            return new Response(false, e.getMessage(), null);
        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        } catch (ServerStorageException e) {
            return new Response(false, e.getMessage(), null);
        }

    }

}
