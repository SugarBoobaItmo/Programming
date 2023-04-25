package handlers;

import java.net.SocketAddress;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;

public class RemoveKeyHandler extends Handler{
    private String name = "remove_key";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) {
        String key = (String)request.getData().get("argument");
        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId.toString());
            if (collectionRecord.getCollection().containsKey(key)) {
                collectionRecord.getCollection().remove(key);
                CollectionStorage.save(userId.toString(), collectionRecord);
                return new Response(true, "StudyGroup removed successfully", null);
            } else {
                return new Response(false, "StudyGroup not found", null);
            }
        } catch (ServerStorageException e) {
            // TODO Auto-generated catch block
            return new Response(false, e.getMessage(), null);
        }
    }


}
