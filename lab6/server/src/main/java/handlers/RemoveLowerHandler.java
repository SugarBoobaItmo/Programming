package handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import database.DatabaseManager;
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
public class RemoveLowerHandler extends Handler {
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
     * 
     * @param request The request to handle.
     * @param userId  The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) throws Exception {
        CollectionRecord collectionRecord = CollectionStorage.getCollectionRecord();
        StudyGroup lower_group = (StudyGroup) request.getData().get("object");

        DatabaseManager databaseManager = new DatabaseManager();
        Connection connection = databaseManager.getConnection();

        Long studentsCount = lower_group.getStudentsCount();
        String sql = "DELETE FROM groups WHERE students_count < ? AND owner_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        if (studentsCount == null) {
            studentsCount = 0L;
        }
        statement.setLong(1, studentsCount);
        statement.setString(2, userId);
        statement.executeUpdate();

        synchronized (collectionRecord.getCollection()) {
            Iterator<Map.Entry<String, StudyGroup>> iterator = collectionRecord.getCollection().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, StudyGroup> entry = iterator.next();
                if (entry.getValue().compareTo(lower_group) < 0 && entry.getValue().getOwner().equals(userId)) {
                    iterator.remove();
                }
            }
        }
        // collectionRecord.setCollection(CollectionStorage.load(userId.toString()).getCollection());
        connection.close();

        HashMap<String, Object> data = new HashMap<>();
        data.put("object", collectionRecord);

        return new Response(true, "Lower groups were dropped successfully", data);
    }
}
