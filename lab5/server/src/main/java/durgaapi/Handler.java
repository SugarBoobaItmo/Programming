package durgaapi;

import java.net.SocketAddress;


public abstract class Handler {

    public abstract String getName();

    public abstract Response handle(Request request, String userId);

}
