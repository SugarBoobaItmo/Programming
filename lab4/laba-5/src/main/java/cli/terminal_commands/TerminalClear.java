package cli.terminal_commands;

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
        super("clear", "Clears the screen");
    
    }

    /**
     * Executes the terminal command.
     */
    @Override
    public void execute() {  
        System.out.print("\033[H\033[2J"); // characters sequence for output cleaning 
        System.out.flush();  
    }  
}
