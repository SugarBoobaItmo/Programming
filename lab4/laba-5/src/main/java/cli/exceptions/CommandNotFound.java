package cli.exceptions;


public class CommandNotFound extends Exception {
    public CommandNotFound(String commandName) {
        super("No such command " + commandName);
    }
}
