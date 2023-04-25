package handlers.exceptions;

public class ServerStorageException extends Exception{
    private String message;

    public ServerStorageException() {
        this.message = "Something went wrong with the server storage. Please, try again later.";
    }

    public ServerStorageException(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
}
