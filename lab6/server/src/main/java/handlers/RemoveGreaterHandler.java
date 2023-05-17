package handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import database.DatabaseManager;
import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import models.CollectionRecord;
import models.StudyGroup;

/**
 * Handler for the "remove_greater" command.
 * Removes all elements from the collection that are greater than the given one.
 */
public class RemoveGreaterHandler extends Handler {

    private String name = "remove_greater";

    /**
     * @return The name of the command.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Removes all elements from the collection that are greater than the given one.
     * 
     * @param request The request to handle.
     * @param userId  The id of the user who sent the request.
     * @return The response to the request.
     */
    @Override
    public Response handle(Request request, String userId) throws Exception {
        CollectionRecord collectionRecord = CollectionStorage.getCollectionRecord();

        StudyGroup greater_group = (StudyGroup) request.getData().get("object");

        DatabaseManager databaseManager = new DatabaseManager();
        Connection connection = databaseManager.getConnection();

        Long studentsCount = greater_group.getStudentsCount();
        String sql = "DELETE FROM groups WHERE students_count > ? AND owner_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        if (studentsCount == null) {
            studentsCount = 0L;
        }
        statement.setLong(1, studentsCount);
        statement.setString(2, userId);
        statement.executeUpdate();

        // synchronized (collectionRecord.getCollection()) {
        //     collectionRecord.getCollection().removeIf(studyGroup -> studyGroup.compareTo(greater_group) > 0);
        // }
        synchronized (collectionRecord.getCollection()) {
            Iterator<Map.Entry<String, StudyGroup>> iterator = collectionRecord.getCollection().entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, StudyGroup> entry = iterator.next();
                if (entry.getValue().compareTo(greater_group) > 0 && entry.getValue().getOwner().equals(userId)) {
                    iterator.remove();
                }
            }
        }
        
        connection.close();

        HashMap<String, Object> data = new HashMap<>();
        data.put("object", collectionRecord);

        return new Response(true, "Greater groups were dropped successfully", data);
    }
}
