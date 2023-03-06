package cli.terminal_commands;

public abstract class TerminalCommand {
    protected String name;
    protected String description;
    
    public TerminalCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public abstract void execute();
    
}
