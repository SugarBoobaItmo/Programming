package durgaapi;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import durgaapi.exceptions.NotHandledException;

public class Reciever {
    List<Handler> handlers = new ArrayList<Handler>();

    public Reciever() {

    }

    public void registerHandler(Handler handler) {
        handlers.add(handler);
    }

    public Response handleRequest(Request request, SocketAddress userId) throws NotHandledException{
        for (Handler handler : handlers) {
            if (request.getCommand().equals(handler.getName())) {
                return handler.handle(request, userId);
            }
        }
        throw new NotHandledException();
    }


}
