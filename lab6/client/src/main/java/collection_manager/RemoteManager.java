package collection_manager;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import cli.commands.exceptions.GroupNotFound;
import durgaapi.Response;
import models.CollectionRecord;
import models.StudyGroup;
import serveradapter.ServerAdapter;

public class RemoteManager extends AbstractManager {
    private ServerAdapter serverAdapter;

    public RemoteManager(ServerAdapter serverAdapter) {
        this.serverAdapter = serverAdapter;
        // load collection record from server
        this.loadCollectionRecord();

    }

    @Override
    public void loadCollectionRecord() {
        try {
            Response response = serverAdapter.sendRequest("get_collection_record", null);
            // if the response is ok, set the collection record
            if (response.isOk()) {
                this.collectionRecord = (CollectionRecord) response.getData().get("object");
            } else {
                System.out.println(response.getDetail());
                System.exit(0);
            }
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
        } catch (IOException e) {
            System.out.println("Error: IO exception");
        }

    }

    @Override
    public Response insert(String index, StudyGroup group) {
        String command = "insert";
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("argument", index);
        data.put("object", group);

        try {
            Response response = serverAdapter.sendRequest(command, data);
            if (response.isOk()){
                this.collectionRecord = (CollectionRecord) response.getData().get("object");
            }
            return response;

        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
        } catch (IOException e) {
            System.out.println("Error: IO exception");
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
            this.collectionRecord = (CollectionRecord) response.getData().get("object");
            return response;
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
        } catch (IOException e) {
            System.out.println("Error: IO exception");
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
            this.collectionRecord = (CollectionRecord) response.getData().get("object");
            return response;
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
        } catch (IOException e) {
            System.out.println("Error: IO exception");
        }
        return new Response(false, "The group was not removed", null);
    }

    @Override
    public Response clear() {
        String command = "clear";
        HashMap<String, Object> data = new HashMap<String, Object>();

        try {
            Response response = serverAdapter.sendRequest(command, data);
            this.collectionRecord = (CollectionRecord) response.getData().get("object");
            return response;
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
        } catch (IOException e) {
            System.out.println("Error: IO exception");
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
            this.collectionRecord = (CollectionRecord) response.getData().get("object");
            return response;
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
        } catch (IOException e) {
            System.out.println("Error: IO exception");
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
            this.collectionRecord = (CollectionRecord) response.getData().get("object");
            return response;
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
        } catch (IOException e) {
            System.out.println("Error: IO exception");
        }
        return new Response(false, "The group was not removed", null);
    }

}
