package cli.commands.exceptions;

/**
 * 
 * An exception class that is thrown when a parameter contains non-letter
 * characters.
 */
public class IncorrectWord extends ExecuteError {
    /**
     * 
     * Constructs a new IncorrectWord object with the given parameter value.
     * 
     * @param value the parameter value that caused the exception
     */
    public IncorrectWord(String value) {
        super(String.format(
                "Parameter must contain only literals, but \"%s\" taken",
                value));
    }
}
