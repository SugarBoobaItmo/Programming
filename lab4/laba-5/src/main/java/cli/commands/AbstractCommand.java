package cli.commands;

import java.util.List;

import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

/**
 * 
 * An abstract class that represents a command.
 */
public abstract class AbstractCommand {
    /** The name of the command. */
    private String name;
    /** The description of the command. */
    private String description;

    /**
     * 
     * Constructs a new AbstractCommand object with the specified name and
     * description.
     * 
     * @param name        The name of the command.
     * @param description The description of the command.
     */
    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 
     * Returns the name of the command.
     * 
     * @return The name of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Returns the description of the command.
     * 
     * @return The description of the command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * Executes the command with the specified inline parameters, input and output.
     * 
     * @param inlineParams The list of inline parameters.
     * @param input        The input reader.
     * @param output       The output writer.
     * @throws ExecuteError if an error occurs during the execution of the command.
     */
    public abstract void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError;

}
