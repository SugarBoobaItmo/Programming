package cli.terminal_commands;

public class TerminalClear extends TerminalCommand {
    public TerminalClear() {
        super("clear", "Clears the screen");
    
    }
    @Override
    public void execute() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}
