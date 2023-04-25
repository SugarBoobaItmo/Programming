package cli.commands;

import java.util.ArrayList;
import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

/**
 * 
 * SaveCommand is a class that represents a command to save the current
 * collection to a file in CSV format.
 * 
 */
public class SaveCommand extends AbstractCollectionCommand {

    /**
     * 
     * Constructor for the SaveCommand class.
     * 
     * @param manager An instance of the AbstractManager class.
     */
    public SaveCommand(AbstractManager manager) {
        super("Save", "Save collection to file", new ArrayList<String>(), manager);
    }

    /**
     * 
     * Executes the command to save the collection to a file.
     * 
     * @param inlineParams The list of parameters provided with the command.
     * @param input        The LineReader to read input from.
     * @param output       The LineWriter to write output to.
     * @throws ExecuteError If there is an error executing the command.
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        manager.save();
        System.out.println("Collection saved to file");
    }
}
