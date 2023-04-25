package handlers;

import java.net.SocketAddress;
import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;

public class GetCollectionRecordHandler extends Handler{
    private String name = "get_collection_record";
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) {
        CollectionRecord collectionRecord;
        try {
            collectionRecord = CollectionStorage.load(userId.toString());
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);
            return new Response(true, "CollectionRecord loaded successfully", data);
        } catch (ServerStorageException e) {
            // TODO Auto-generated catch block
            return new Response(false, e.getMessage(), null);
        }

    }
    
}
