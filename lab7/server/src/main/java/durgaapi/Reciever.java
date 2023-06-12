package durgaapi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import durgaapi.exceptions.NotHandledException;

/**
 * Reciever
 * 
 * This class is responsible for handling the requests.
 */
public class Reciever {
    // The list of handlers
    List<Handler> handlers = new ArrayList<Handler>();

    public Reciever() {
    }

    /**
     * Register a handler
     * 
     * @param handler The handler to register
     */
    public void registerHandler(Handler handler) {
        handlers.add(handler);
    }

    /**
     * Handle a request
     * 
     * @param request The request to handle
     * @param string  The user id
     * @return The response
     * @throws NotHandledException
     */
    public Response handleRequest(Request request, String string) throws NotHandledException {
        for (Handler handler : handlers) {
            if (request.getCommand().equals(handler.getName())) {
                try {
                    return handler.handle(request, string);
                } catch (SQLException e) {
                    return new Response(false, "Something went wrong on server", null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Response(false, "Something went wrong on server", null);
                }
            }
        }
        throw new NotHandledException();
    }

}
