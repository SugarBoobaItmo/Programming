package handlers;

import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;

/**
 * Handler for the "info" command.
 * Shows information about the collection.
 */
public class InfoHandler extends Handler {
    private String name = "info";

    /**
     * @return The name of the command.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Shows information about the collection.
     * @param request The request to handle.
     * @param userId The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) {
        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId);
            String info = collectionRecord.getInfo().toString() + " size= " + collectionRecord.getCollection().size();
            
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);

            return new Response(true, info, data);
        } catch (ServerStorageException e) {
            return new Response(false, e.getMessage(), null);
        }
    }

}
