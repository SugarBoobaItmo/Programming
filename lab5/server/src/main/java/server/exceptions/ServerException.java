package server.exceptions;

/**
 * ServerInitException
 * 
 * This exception is thrown when the server initialization fails.
 */
public class ServerException extends Exception {
    private String message;

    /**
     * Constructor with default message
     */
    public ServerException() {
        this.message = "Something went wrong with the server. Please, try again later.";
    }

    /**
     * Constructor with custom message
     * 
     * @param message
     */
    public ServerException(String message) {
        this.message = message;
    }

    /**
     * method to get the message from the exception
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

}
