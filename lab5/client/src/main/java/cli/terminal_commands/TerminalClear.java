package cli.terminal_commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;

/**
 * 
 * The TerminalClear class represents a terminal command to clear the screen.
 * 
 */
public class TerminalClear extends TerminalCommand {
    /**
     * Constructs a new TerminalClear instance.
     */
    public TerminalClear() {
        super("/clear", "Clears the screen");
    
    }

    /**
     * Executes the terminal command.
     */
    @Override
    public void execute(List<String> param) throws ExecuteError {  
        
        Checkers.checkInlineParamsCount(0, param);
        System.out.print("\033[H\033[2J"); // characters sequence for output cleaning 
        System.out.flush();  
    }  
}
