package cli.terminal_commands;

import java.util.List;

import cli.commands.exceptions.ExecuteError;

/**
 * 
 * A base class for terminal commands.
 */
public abstract class TerminalCommand {
    /**
     * 
     * The name of the command.
     */
    protected String name;
    /**
     * 
     * The description of the command.
     */
    protected String description;

    /**
     * 
     * Constructs a new TerminalCommand object with the given name and description.
     * 
     * @param name        the name of the command
     * @param description the description of the command
     */
    public TerminalCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 
     * Returns the name of the command.
     * 
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Returns the description of the command.
     * 
     * @return the description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * Executes the command.
     */
    public abstract void execute(List<String> param) throws ExecuteError;

}
