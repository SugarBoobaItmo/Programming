package cli.commands.exceptions;

/**
 * 
 * An exception thrown when a group is not found.
 */
public class GroupNotFound extends ExecuteError {
    /**
     * 
     * Constructs a new GroupNotFound exception.
     */
    public GroupNotFound() {
        super("Group not found");
    }    
}
