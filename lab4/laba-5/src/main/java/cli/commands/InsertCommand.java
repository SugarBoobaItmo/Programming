package cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;

import models.StudyGroup;

/**
 * 
 * The InsertCommand class represents a command that inserts an element to the
 * collection.
 * 
 */
public class InsertCommand extends AbstractCollectionCommand {

    /**
     * 
     * Constructs an InsertCommand object with the specified manager.
     * 
     * @param manager the AbstractManager object to manage the collection
     */
    public InsertCommand(AbstractManager manager) {
        super("Insert", "Insert element to collection null", new ArrayList<String>(Arrays.asList("{element}")), manager);
    }

    /**
     * 
     * Executes the command to insert an element to the collection.
     * 
     * @param inlineParams the list of inline parameters provided with the command
     * 
     * @param input        the LineReader object to read input from the console
     * 
     * @param output       the LineWriter object to write output to the console
     * 
     * @throws ExecuteError if there was an error executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts)
            throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);

        // check if key already exists
        if (manager.getCollection().containsKey(inlineParams.get(1))) {
            output.writeLine("Key already exists" + "\n");
            return;
        }
        // logic for reading insertion from script
        StudyGroup studyGroup;
        if (disableAttempts) {
            studyGroup = ElementCommand.readScriptElement(input, output);

        } else {
            studyGroup = ElementCommand.readElement(input, output);
        }

        if (studyGroup != null) {
            manager.insert(inlineParams.get(1), studyGroup);

        } else {
            output.writeLine("Study group not found" + "\n");
            return;
        }
    }

}
