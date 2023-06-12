package cli.commands.exceptions;

/**
 * 
 * An exception thrown when the number of inline parameters is incorrect.
 */
public class IncorrectInlineParamsCount extends ExecuteError {
    /**
     * 
     * Constructs a new IncorrectInlineParamsCount exception with the given
     * expected and taken parameters count.
     * 
     * @param expected The expected parameters count.
     * @param taken    The taken parameters count.
     */
    public IncorrectInlineParamsCount(int expected, int taken) {
        super(String.format(
           "Unexpected inline parameters count: %d expected but %d taken",
            expected, taken
        ));
    } 
}
