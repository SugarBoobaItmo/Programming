package cli.commands.exceptions;

/**
 * 
 * An exception indicating that a parameter is null.
 */
public class NullParam extends ExecuteError {
    /**
     * 
     * Constructs a new NullParam with the default error message "Parameter
     * is null".
     */
    public NullParam() {
        super("Parameter is null");
    }
}
