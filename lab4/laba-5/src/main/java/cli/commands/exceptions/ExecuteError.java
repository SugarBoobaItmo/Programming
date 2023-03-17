package cli.commands.exceptions;

/**
 * 
 * An exception thrown when an error occurs during the execution of a command.
 */
public class ExecuteError extends Exception {
    /**
     * 
     * Constructs a new ExecuteError exception with the given message.
     * 
     * @param message The message of the exception.
     */
    public ExecuteError(String message) {
        super(message);
    }
}
