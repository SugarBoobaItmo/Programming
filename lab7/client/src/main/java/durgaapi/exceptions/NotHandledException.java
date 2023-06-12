package durgaapi.exceptions;

public class NotHandledException extends Exception {
    public NotHandledException() {
        // super(message);
        super("The request was not handled.");

    }    
}
