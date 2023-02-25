package cli.commands.exceptions;

public class NullParam extends ExecuteError {
    public NullParam() {
        super("Parameter is null");
    }    
}
