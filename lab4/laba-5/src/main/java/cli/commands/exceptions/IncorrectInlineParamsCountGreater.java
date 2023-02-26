package cli.commands.exceptions;


public class IncorrectInlineParamsCountGreater extends ExecuteError {
    public IncorrectInlineParamsCountGreater(int expected, int taken) {
        super(String.format(
           "Unexpected inline parameters count: expected more than %d but %d taken",
            expected, taken
        ));
    } 
}
