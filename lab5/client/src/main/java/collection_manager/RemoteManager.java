package collection_manager;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import cli.ServerAdapter;
import cli.commands.exceptions.GroupNotFound;
import durgaapi.Response;
import models.CollectionRecord;
import models.StudyGroup;

public class RemoteManager extends AbstractManager {
    private ServerAdapter serverAdapter;

    public RemoteManager(ServerAdapter serverAdapter) {
        super();
        this.serverAdapter = serverAdapter;
        
        this.setCollectionRecord();
        
    }

    @Override
    public void setCollectionRecord(){
        try {
            Response response = serverAdapter.sendRequest("get_collection_record", null);
            if (response.isOk()){

                this.collectionRecord = (CollectionRecord) response.getData().get("object");
            }   else {
                System.out.println(response.getDetail());
                System.exit(1);
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            System.out.println("Error: Unknown host");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: IO exception");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public Response insert(String index, StudyGroup group) {
        String command = "insert";
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("argument", index);
        data.put("object", group);
        try {
            // serverAdapter.sendRequest(command, data)
            // serverAdapter.sendRequest(command, data);
            Response response = serverAdapter.sendRequest(command, data);

            setCollectionRecord();
            return response;

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Response(false, "The group was not inserted", null);
    }

    @Override
    public Response update(String index, StudyGroup group) {
        String command = "update";
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("argument", index);
        data.put("object", group);
        try {
            Response response = serverAdapter.sendRequest(command, data);
            setCollectionRecord();
            return response;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Response(false, "The group was not updated", null);
    }

    @Override
    public Response removeGreater(StudyGroup greater_group) {
        String command = "remove_greater";
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("object", greater_group);
        try {
            Response response = serverAdapter.sendRequest(command, data);
            setCollectionRecord();
            return response;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Response(false, "The group was not removed", null);
    }

    @Override
    public Response clear() {
        String command = "clear";
        HashMap<String, Object> data = new HashMap<String, Object>();

        try {
            Response response = serverAdapter.sendRequest(command, data);
            setCollectionRecord();
            return response;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Response(false, "The collection was not cleared", null);
    }

    @Override
    public Response removeLower(StudyGroup lower_group) {
        String command = "remove_lower";
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("object", lower_group);

        try {
            Response response = serverAdapter.sendRequest(command, data);
            setCollectionRecord();
            return response;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Response(false, "The group was not removed", null);
    }

    @Override
    public Response removeKey(String key) throws GroupNotFound {
        String command = "remove_key";
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("argument", key);
        try {
            Response response = serverAdapter.sendRequest(command, data);
            setCollectionRecord();
            return response;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Response(false, "The group was not removed", null);
    }   


}
