package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

/**
 * 
 * The InfoCommand class represents a command that prints information about the
 * collection.
 * 
 */
public class InfoCommand extends AbstractCollectionCommand {
    /**
     * 
     * Constructs a new InfoCommand with the specified collection manager.
     * 
     * @param manager the collection manager to be used
     */
    public InfoCommand(AbstractManager manager) {
        super("Info", "Write info about collection", manager);
    }

    /**
     * 
     * Executes the InfoCommand by printing information about the collection.
     * 
     * @param inlineParams the list of parameters passed inline with the command
     * @param input        the LineReader used to read input from the console (not
     *                     used in this method)
     * @param output       the LineWriter used to write output to the console
     * @throws ExecuteError if an error occurs while executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        output.writeLine(manager.getInfo().toString() + "\n");
    }
}
