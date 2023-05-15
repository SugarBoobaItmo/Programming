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
 * Handler for the "clear" command.
 * Clears the collection.
 */
public class ClearHandler extends Handler{
    private String name = "clear";
    
    /**
     * @return The name of the command.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Clears the collection.
     * @param request The request to handle.
     * @param userId The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) {
        
            DatabaseManager dbManager = new DatabaseManager();
            try {
                Connection connection = dbManager.getConnection();
                String sql = "DELETE FROM groups WHERE owner_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, userId);
                statement.executeUpdate();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("object", new CollectionRecord());
            
            
            return new Response(true, "Your groups were cleared successfully", data);
        
    }
    
}
