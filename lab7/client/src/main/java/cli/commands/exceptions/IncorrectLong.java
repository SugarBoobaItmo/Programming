package cli.commands.exceptions;

/**
 * 
 * An exception indicating that a parameter is expected to be a long but is not.
 */
public class IncorrectLong extends ExecuteError {
    /*
     * Constructs a new IncorrectLong exception with the given value.
     * 
     * @param value the incorrect value
     */
    public IncorrectLong(String value) {
        super(String.format(
                "Parameter must be long type, but \"%s\" taken",
                value));
    }
}
