package cli.commands.exceptions;

/**
 * 
 * An ExecuteError subclass for when a parameter is expected to be positive, but
 * a non-positive value is taken.
 */
public class NotPositive extends ExecuteError {
    /**
     * 
     * Constructs a NotPositive exception with the given value.
     * 
     * @param value the value that caused the exception
     */
    public NotPositive(String value) {
        super(String.format(
                "Parameter must be positive, but \"%s\" taken",
                value));
    }
}
