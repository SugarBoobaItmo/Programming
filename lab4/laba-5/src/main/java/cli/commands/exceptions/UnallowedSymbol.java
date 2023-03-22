package cli.commands.exceptions;

/**
 * 
 * An exception class to represent a string that contains an unallowed symbol.
 */
public class UnallowedSymbol extends ExecuteError {
    /**
     * 
     * Constructs an UnallowedSymbol exception with a default message.
     * @param message the message to set
     */
    public UnallowedSymbol(String message) {
        super("String with unallowed symbol");
    }

}
