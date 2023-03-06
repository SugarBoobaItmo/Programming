package cli.commands.exceptions;

public class GroupNotFound extends ExecuteError {
    public GroupNotFound() {
        super("Group not found");
    }    
}
