package handlers;

import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import models.CollectionRecord;

/**
 * Handler for the "show" command.
 * Shows the collection.
 */
public class ShowHandler extends Handler {
    private String name = "show";

    /**
     * @return The name of the command.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Shows the collection.
     * 
     * @param request The request to handle.
     * @param userId  The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) throws Exception {
        CollectionRecord collectionRecord;
        // collectionRecord = CollectionStorage.load(userId);
        synchronized (CollectionRecord.class) {
            collectionRecord = CollectionStorage.getCollectionRecord();
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("object", collectionRecord);

        return new Response(true, "Collection record was successfully loaded", data);
    }
}
