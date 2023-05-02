package handlers;


import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;

/**
 * Handler for the "remove_key" command.
 * Removes an element from the collection by its key.
 */
public class RemoveKeyHandler extends Handler{
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
     * @param request The request to handle.
     * @param userId The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) {

        String key = (String)request.getData().get("argument");
        CollectionRecord collectionRecord;

        try {
            collectionRecord = CollectionStorage.load(userId);
            HashMap<String, Object> data = new HashMap<>();
            // if collection contains key, remove it
            if (collectionRecord.getCollection().containsKey(key)) {
                collectionRecord.getCollection().remove(key);
                
                data.put("object", collectionRecord);
                return new Response(true, "StudyGroup removed successfully", data);
            } else {
                
                data.put("object", collectionRecord);
                return new Response(false, "StudyGroup not found", data);
            }
        } catch (ServerStorageException e) {
            return new Response(false, e.getMessage(), null);
        }
    }


}
