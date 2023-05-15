package handlers.exceptions;

/**
 * 
 * The ServerStorageException class is a custom exception that is thrown when an
 * error occurs with the server storage.
 */
public class ServerStorageException extends Exception {
    private String message;

    /**
     * 
     * Constructs a new ServerStorageException object with a default error message.
     */
    public ServerStorageException() {
        this.message = "Something went wrong with the server storage. Please, try again later.";
    }

    /**
     * 
     * Constructs a new ServerStorageException object with the specified error
     * message.
     * 
     * @param message the error message to set
     */
    public ServerStorageException(String message) {
        this.message = message;
    }

    /**
     * 
     * Returns the error message associated with this exception.
     * 
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

}
