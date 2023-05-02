package handlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import models.StudyGroup;

public class UpdateHandler extends Handler {

    private String name = "update";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) {
        Random random = new Random();

        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId);
            String argument = (String) request.getData().get("argument");
            StudyGroup studyGroup = (StudyGroup) request.getData().get("object");

            // generate random id and birthday
            studyGroup.setId(random.nextInt(1000000));
            LocalDateTime date = LocalDateTime.now().minusYears(random.nextInt(3) - 17);

            if (studyGroup.getGroupAdmin() != null) {
                studyGroup.getGroupAdmin().setBirthday(date);
            }

            // check if id exists
            for (Map.Entry<String, StudyGroup> entry : collectionRecord.getCollection().entrySet()) {
                if (entry.getValue().getId() == Integer.parseInt(argument)) {
                    
                    collectionRecord.getCollection().put(entry.getKey(), studyGroup);

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("object", collectionRecord);

                    return new Response(true, "StudyGroup updated successfully", data);

                }
            }
            // if key doesn't exist return error
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);
          
            return new Response(false, "StudyGroup with id: \"" + argument + "\" not found", data);
        } catch (ServerStorageException e) {
            return new Response(false, e.getMessage(), null);
        }
    }

}
