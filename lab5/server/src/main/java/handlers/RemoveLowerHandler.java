package handlers;


import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import models.StudyGroup;

/**
 * Handler for the "remove_lower" command.
 * Removes all groups that are lower than the given one.
 */
public class RemoveLowerHandler extends Handler{
    private String name = "remove_lower";
    
    /**
     * @return The name of the command.
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Removes all groups that are lower than the given one.
     * @param request The request to handle.
     * @param userId The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) {
        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId);
            StudyGroup lower_group = (StudyGroup)request.getData().get("object");

            // remove all groups that are lower than the given one
            collectionRecord.getCollection().entrySet().removeIf(entry -> entry.getValue().compareTo(lower_group) < 0);
            
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);

            return new Response(true, "StudyGroups removed successfully", data);
        } catch (ServerStorageException e) {
            return new Response(false, e.getMessage(), null);
        }
    }

    
}
