package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

/**
 * 
 * The ShowCommand class represents a command that displays the entire
 * collection of study groups.
 */
public class ShowCommand extends AbstractCollectionCommand {
    /**
     * 
     * Constructs a new ShowCommand with the specified collection manager.
     * 
     * @param manager the collection manager to be used
     */
    public ShowCommand(AbstractManager manager) {
        super("Show", "Show collection", manager);
    }

    /**
     * 
     * Executes the ShowCommand by displaying the entire collection of study groups.
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
        if (manager.getCollection().size() == 0) {
            output.writeLine("Collection is empty" + "\n");
        } else {
            manager.getCollection().forEach((k, v) -> output.writeLine(k + ": " + v + "\n\n"));
        }
    }
}
