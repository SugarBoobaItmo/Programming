package handlers;

import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import models.CollectionRecord;

/**
 * Handler class for getting a CollectionRecord object associated with a user
 * ID.
 */
public class GetCollectionRecordHandler extends Handler {
    private String name = "get_collection_record";

    /**
     * Returns the name of the command associated with this handler.
     *
     * @return the name of the command.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Handles the request to get the CollectionRecord associated with the user ID.
     *
     * @param request The request to handle.
     * @param userId  The ID of the user making the request.
     * @return A Response object containing the result of the request.
     */
    @Override
    public Response handle(Request request, String userId) throws Exception {
        CollectionRecord collectionRecord;

        synchronized (CollectionRecord.class) {
            collectionRecord = CollectionStorage.load(userId.toString());
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("object", collectionRecord);

        return new Response(true, "CollectionRecord loaded successfully", data);
    }
}
