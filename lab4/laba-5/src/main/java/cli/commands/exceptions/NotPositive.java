package cli.commands.exceptions;

public class NotPositive extends ExecuteError {
    public NotPositive(String value) {
        super(String.format(
                "Parameter must be positive, but \"%s\" taken",
                value));
    }
}
