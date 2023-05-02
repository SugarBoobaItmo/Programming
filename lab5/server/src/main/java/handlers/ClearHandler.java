package handlers;

import java.util.HashMap;

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
        CollectionRecord collectionRecord;
        try {
            // load collection record from storage and clear it
            collectionRecord = CollectionStorage.load(userId);
            collectionRecord.getCollection().clear();
            
            // put the collection record into the response and send it
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);
            
            
            return new Response(true, "Collection cleared successfully", data);
        } catch (ServerStorageException e) {
            return new Response(false, e.getMessage(), null);
        }
    }
    
}
