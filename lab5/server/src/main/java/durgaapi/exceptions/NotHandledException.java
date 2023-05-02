package durgaapi.exceptions;

/**
 * NotHandledException
 * 
 * This exception is thrown when the request is not handled.
 */
public class NotHandledException extends Exception {
    
    public NotHandledException() {
        super("The request was not handled.");

    }    
}
