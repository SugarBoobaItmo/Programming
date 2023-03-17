package cli.exceptions;

/**
 * 
 * An exception thrown when a command is not found.
 */
public class CommandNotFound extends Exception {
    /**
     * 
     * Constructs a new CommandNotFound exception with the given command name.
     * 
     * @param commandName the name of the command
     */
    public CommandNotFound(String commandName) {
        super("No such command " + commandName);
    }
}
