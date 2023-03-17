package cli.commands;

import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

/**
 * 
 * The RemoveKeyCommand class represents a command that removes an element from
 * the collection by its key.
 * 
 */
public class RemoveKeyCommand extends AbstractCollectionCommand {
    /**
     * 
     * Constructs a new RemoveKeyCommand with the specified collection manager.
     * 
     * @param manager the collection manager to be used
     */
    public RemoveKeyCommand(AbstractManager manager) {
        super("RemoveKey", "Remove element by key", manager);
    }

    /**
     * 
     * Executes the RemoveKeyCommand by removing an element from the collection by
     * its key.
     * 
     * @param inlineParams the list of parameters passed inline with the command
     * @param input        the LineReader used to read input from the console (not
     *                     used in this method)
     * @param output       the LineWriter used to write output to the console
     * @throws ExecuteError if an error occurs while executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        manager.removeKey(inlineParams.get(1));
    }
}
