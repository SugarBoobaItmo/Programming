package handlers;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import fqme.connection.ConnectionManager;
import fqme.models.StudyGroupModel;
import fqme.view.View;
import models.CollectionRecord;
import models.StudyGroup;

/**
 * Handler for the "clear" command.
 * Clears the collection.
 */
public class ClearHandler extends Handler {
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
     * 
     * @param request The request to handle.
     * @param userId  The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) throws Exception {
        CollectionRecord collectionRecord = CollectionStorage.getCollectionRecord();

        try (Connection connection = ConnectionManager.getConnection(StudyGroupModel.class)) {

            // delete all groups owned by the user
            View<StudyGroupModel> groupView = View.of(StudyGroupModel.class, connection);
            groupView.delete(StudyGroupModel.ownerId_.eq(Integer.parseInt(userId)));
            connection.close();

            // remove all groups owned by the user from the collection
            synchronized (collectionRecord.getCollection()) {
                Iterator<Map.Entry<String, StudyGroup>> iterator = collectionRecord.getCollection().entrySet()
                        .iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, StudyGroup> entry = iterator.next();
                    if (entry.getValue().getOwner().equals(userId)) {
                        iterator.remove();
                    }
                }
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);

            return new Response(true, "Your groups were cleared successfully", data);
        }
    }
}
