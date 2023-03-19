package cli.commands.exceptions;

/**
 * Exception that is thrown when a command is executed with more inline
 * parameters than expected.
 */
public class IncorrectInlineParamsCountLower extends ExecuteError {
    /**
     * Constructs an IncorrectInlineParamsCountLower exception with the given
     * expected and actual number of inline parameters.
     * 
     * @param expected the expected number of inline parameters
     * @param taken    the actual number of inline parameters taken
     */
    public IncorrectInlineParamsCountLower(int expected, int taken) {
        super(String.format(
                "Unexpected inline parameters count: expected less than %d but %d taken",
                expected, taken));
    }

    /**
     * Constructs an IncorrectInlineParamsCountLower exception with the given
     * expected number of inline parameters.
     * 
     * @param expected the expected number of inline parameters
     */
    public IncorrectInlineParamsCountLower(int expected) {
        super(String.format(
                "Unexpected inline parameters count: expected less than %d",
                expected));
    }
}