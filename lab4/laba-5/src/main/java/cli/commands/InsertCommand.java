package cli.commands;

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
public class InsertCommand extends ElementCommand {

    /**
     * 
     * Constructs an InsertCommand object with the specified manager.
     * 
     * @param manager the AbstractManager object to manage the collection
     */
    public InsertCommand(AbstractManager manager) {
        super("Insert", "Insert element to collection null -{element}", manager);
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
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCountGreater(1, inlineParams);

        // check if key already exists
        if (manager.getCollection().containsKey(inlineParams.get(1))) {
            output.writeLine("Key already exists" + "\n");
            return;
        }
        // logic for reading insertion from script
        StudyGroup studyGroup;
        if (inlineParams.size() > 2) {
            studyGroup = this.readElement(
                    Arrays.copyOfRange(inlineParams.toArray(new String[inlineParams.size()]), 2, inlineParams.size()),
                    input, output);

        } else
            studyGroup = this.readElement(input, output);

        if (studyGroup != null) {
            manager.insert(inlineParams.get(1), studyGroup);

        } else {
            output.writeLine("Study group not found" + "\n");
            return;
        }
    }

}
