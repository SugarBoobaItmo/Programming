package handlers;

import java.util.HashMap;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import models.CollectionRecord;

/**
 * A subclass of Handler that handles the "exit" command from the client.
 * Saves the CollectionRecord of the user to a file and removes it from the
 * connection map.
 */
public class ExitHandler extends Handler {
    private String name = "exit";

    /**
     * Returns the name of the command.
     * 
     * @return Command name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Handles the "exit" command from the client.
     * Saves the CollectionRecord of the user to a file and removes it from the
     * connection map.
     * 
     * @param request The request sent by the client.
     * @param userId  The ID of the user.
     * @return A Response object containing information about the success of the
     *         operation and a message for the client.
     */
    @Override
    public Response handle(Request request, String userId) {
        HashMap<String, CollectionRecord> con = CollectionStorage.getConnectionMap();
        if (con.containsKey(userId)) {
            con.remove(userId);
        }
        return new Response(true, "Client is shutting down", null);
    }
}
