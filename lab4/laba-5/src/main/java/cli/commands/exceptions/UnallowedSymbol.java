package cli.commands.exceptions;

public class UnallowedSymbol extends ExecuteError{
    public UnallowedSymbol(String message) {
        super("String with unallowed symbol");
    }
    
}
