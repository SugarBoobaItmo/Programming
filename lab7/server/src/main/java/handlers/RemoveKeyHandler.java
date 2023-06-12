package handlers;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import fqme.connection.ConnectionManager;
import fqme.models.StudyGroupModel;
import fqme.view.View;
import models.CollectionRecord;
import models.StudyGroup;

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
    public Response handle(Request request, String userId) throws Exception {
        CollectionRecord collectionRecord = CollectionStorage.getCollectionRecord();

        String key = (String) request.getData().get("argument");

        try (Connection connection = ConnectionManager.getConnection(StudyGroupModel.class)) {
            View<StudyGroupModel> groupView = View.of(StudyGroupModel.class, connection);

            Set<StudyGroupModel> deletedGroups = groupView
                    .delete(StudyGroupModel.key_.eq(key).and(StudyGroupModel.ownerId_.eq(Integer.parseInt(userId))));

            synchronized (collectionRecord.getCollection()) {
                Iterator<Map.Entry<String, StudyGroup>> iterator = collectionRecord.getCollection().entrySet()
                        .iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, StudyGroup> entry = iterator.next();
                    if (entry.getKey().equals(key) && entry.getValue().getOwner().equals(userId)) {
                        iterator.remove();
                    }
                }
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);

            if (deletedGroups.isEmpty()) {
                return new Response(false, "You haven't got group with key = '" + key + "'", data);
            } else {
                return new Response(true, "StudyGroup removed successfully", data);
            }
        }
    }
}
