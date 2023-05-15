package durgaapi;

/**
 * Handler
 * 
 * This is an abstract class that is used to handle requests.
 */
public abstract class Handler {
    // The name of the handler
    public abstract String getName();

    // The method that handles the request
    public abstract Response handle(Request request, String userId);
}
