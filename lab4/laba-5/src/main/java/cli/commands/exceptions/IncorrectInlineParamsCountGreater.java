package cli.commands.exceptions;

/**
 * Exception that is thrown when a command is executed with less inline
 * parameters than expected.
 */
public class IncorrectInlineParamsCountGreater extends ExecuteError {
    /**
     * Constructs an IncorrectInlineParamsCountGreater exception with the given
     * expected and actual number of inline parameters.
     * 
     * @param expected the expected number of inline parameters
     * @param taken    the actual number of inline parameters taken
     */
    public IncorrectInlineParamsCountGreater(int expected, int taken) {
        super(String.format(
                "Unexpected inline parameters count: expected more than %d but %d taken",
                expected, taken));
    }
}
