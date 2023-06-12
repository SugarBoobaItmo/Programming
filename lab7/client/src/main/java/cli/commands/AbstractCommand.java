package cli.commands;

import java.util.ArrayList;
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
    /** The inline parameters of the command. */
    private ArrayList<String> inlineParams;

    /**
     * 
     * Constructs a new AbstractCommand object with the specified name and
     * description.
     * 
     * @param name        The name of the command.
     * @param description The description of the command.
     * @param inlineParams The inline parameters of the command.
     */
    public AbstractCommand(String name, String description, ArrayList<String> inlineParams) {
        this.name = name;
        this.description = description;
        this.inlineParams = inlineParams;
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
     * Returns the inline parameters of the command.
     * 
     * @return The inline parameters of the command.
     */

    public ArrayList<String> getInlineParams() {
        return inlineParams;
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
    public abstract void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError;

}
