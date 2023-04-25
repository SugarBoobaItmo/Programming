package handlers;

import java.net.SocketAddress;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import models.StudyGroup;

public class RemoveLowerHandler extends Handler{
    private String name = "remove_lower";
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Response handle(Request request, String userId) {
        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId.toString());
            StudyGroup lower_group = (StudyGroup)request.getData().get("object");
            collectionRecord.getCollection().entrySet().removeIf(entry -> entry.getValue().compareTo(lower_group) < 0);
            
            CollectionStorage.save(userId.toString(), collectionRecord);
            return new Response(true, "StudyGroups removed successfully", null);
        } catch (ServerStorageException e) {
            // TODO Auto-generated catch block
            return new Response(false, e.getMessage(), null);
        }
    }

    
}
