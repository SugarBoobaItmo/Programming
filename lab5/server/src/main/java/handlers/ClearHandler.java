package handlers;

import java.net.SocketAddress;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;

public class ClearHandler extends Handler{
    private String name = "clear";
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) {
        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId.toString());
            collectionRecord.getCollection().clear();
            
    
            CollectionStorage.save(userId.toString(), collectionRecord);
            return new Response(true, "Collection cleared successfully", null);
        } catch (ServerStorageException e) {
            // TODO Auto-generated catch block
            return new Response(false, e.getMessage(), null);
        }
    }
    
}
