package cli.commands.exceptions;

public class IncorrectWord extends ExecuteError {
    public IncorrectWord(String value) {
        super(String.format(
                "Parameter must contain only literals, but \"%s\" taken",
                value));
    }
}
