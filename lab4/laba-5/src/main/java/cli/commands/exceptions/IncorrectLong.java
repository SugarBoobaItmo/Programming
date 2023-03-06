package cli.commands.exceptions;

public class IncorrectLong extends ExecuteError {
    public IncorrectLong(String value) {
        super(String.format(
                "Parameter must be a number, but \"%s\" taken",
                value));
    }
}
