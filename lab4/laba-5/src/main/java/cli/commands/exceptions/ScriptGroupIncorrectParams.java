package cli.commands.exceptions;

/**
 * 
 * An exception thrown when the parameters for a script group are incorrect.
 */
public class ScriptGroupIncorrectParams extends Exception{

    /**
     * 
     * Constructs a new ScriptGroupIncorrectParams exception.
     */
    public ScriptGroupIncorrectParams() {
        super("incorrect params for script group");
    }
    
}
