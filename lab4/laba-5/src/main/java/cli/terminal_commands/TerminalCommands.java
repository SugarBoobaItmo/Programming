package cli.terminal_commands;

public abstract class TerminalCommands {
    protected String name;
    protected String description;
    
    public TerminalCommands(String name, String description) {
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
