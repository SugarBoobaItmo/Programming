package handlers;

import java.net.SocketAddress;
import java.time.LocalDateTime;
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
            collectionRecord = CollectionStorage.load(userId.toString());
            String argument = (String) request.getData().get("argument");
            StudyGroup studyGroup = (StudyGroup) request.getData().get("object");
    
            studyGroup.setId(random.nextInt(1000000));
                LocalDateTime date = LocalDateTime.now().minusYears(random.nextInt(3) - 17);
                if (studyGroup.getGroupAdmin() != null){
                    studyGroup.getGroupAdmin().setBirthday(date);}
            
            collectionRecord.getCollection().put(argument, studyGroup);
    
            CollectionStorage.save(userId.toString(), collectionRecord);
            return new Response(true, "StudyGroup updated successfully", null);
        } catch (ServerStorageException e) {
            // TODO Auto-generated catch block
            return new Response(false, e.getMessage(), null);
        }
    }

}
