package cli.commands.exceptions;

/**
 * This exception is thrown when a parameter is expected to be an integer but it
 * is not.
 */
public class IncorrectInteger extends ExecuteError {
    /**
     * Constructs an IncorrectInteger object with the given error message.
     * 
     * @param value The value of the parameter that was expected to be an integer
     *              but is not.
     */
    public IncorrectInteger(String value) {
        super(String.format(
                "Parameter must be an integer, but \"%s\" taken",
                value));
    }
}
