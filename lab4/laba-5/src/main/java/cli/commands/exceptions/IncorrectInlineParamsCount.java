package cli.commands.exceptions;


public class IncorrectInlineParamsCount extends ExecuteError {
    public IncorrectInlineParamsCount(int expected, int taken) {
        super(String.format(
           "Unexpected inline parameters count: %d expected but %d taken",
            expected, taken
        ));
    } 
}
