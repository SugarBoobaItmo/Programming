package handlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import models.StudyGroup;

/**
 * 
 * This class represents an Insert Handler, which is responsible for handling
 * requests
 * 
 * to insert a new study group into the collection. It extends the Handler class
 * and implements
 * 
 * the handle method and getName method.
 */
public class InsertHandler extends Handler {

    private String name = "insert";

    /**
     * 
     * Returns the command name of the InsertHandler.
     * 
     * @return The command name of the InsertHandler.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 
     * Handles the request to insert a new study group into the collection.
     * Generates a random id and
     * 
     * birthday for the study group and checks if the key already exists in the
     * collection.
     * 
     * @param request The request to insert a new study group.
     * 
     * @param userId  The id of the user making the request.
     * 
     * @return A Response object indicating whether the study group was added
     *         successfully or not.
     */
    @Override
    public Response handle(Request request, String userId) {
        Random random = new Random();

        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId);
            String argument = (String) request.getData().get("argument");
            StudyGroup studyGroup = (StudyGroup) request.getData().get("object");

            // generate id and birthday
            studyGroup.setId(random.nextInt(1000000));
            LocalDateTime date = LocalDateTime.now().minusYears(random.nextInt(3) - 17);

            if (studyGroup.getGroupAdmin() != null) {
                studyGroup.getGroupAdmin().setBirthday(date);
            }

            // check if key already exists in collection and add new studyGroup
            if (collectionRecord.getCollection().containsKey(argument)) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("object", collectionRecord);

                return new Response(false, "Key already exists", data);
            } else {
                collectionRecord.getCollection().put(argument, studyGroup);

                HashMap<String, Object> data = new HashMap<>();
                data.put("object", collectionRecord);

                return new Response(true, "StudyGroup added successfully", data);
            }
        } catch (ServerStorageException e) {
            return new Response(false, e.getMessage(), null);
        }
    }

}
