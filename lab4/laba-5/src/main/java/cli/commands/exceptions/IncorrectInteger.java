package cli.commands.exceptions;

public class IncorrectInteger extends ExecuteError {
    public IncorrectInteger(String value) {
        super(String.format(
                "Parameter must be an integer, but \"%s\" taken",
                value));
    }
}
