package cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import durgaapi.Response;
import models.StudyGroup;

/**
 * 
 * The UpdateCommand class is a subclass of ElementCommand, which updates an
 * element of the collection by ID.
 */
public class UpdateCommand extends AbstractCollectionCommand {

    /**
     * 
     * Constructs a new UpdateCommand instance.
     * 
     * @param manager the collection manager.
     */
    public UpdateCommand(AbstractManager manager) {
        super("Update", "Update element of collection by ID", new ArrayList<String>(Arrays.asList("id", "{element}")) ,manager);
    }

    /**
     * 
     * Executes the command to update an element of the collection by ID.
     * 
     * @param inlineParams The inline parameters provided with the command.
     * 
     * @param input        The input reader.
     * 
     * @param output       The output writer.
     * 
     * @throws ExecuteError If there is an error executing the command.
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        // Check that the inline parameters are valid.
        Checkers.checkInlineParamsCount(1, inlineParams);
        Checkers.checkLong(inlineParams.get(1));

        // Find the element to update.
        manager.loadCollectionRecord();
        for (Map.Entry<String, StudyGroup> entry : manager.getCollection().entrySet()) {
            if (Integer.parseInt(inlineParams.get(1)) == entry.getValue().getId()) {
                StudyGroup studyGroup;

                if (disableAttempts) {
                    studyGroup = ElementCommand.readScriptElement(input, output);
                } else {
                    studyGroup = ElementCommand.readElement(input, output);
                }
                // Update the element.
                if (studyGroup != null) {
                    Response response = manager.update(inlineParams.get(1), studyGroup);
                    output.writeLine(response.getDetail() + "\n");
                }
                return;
            }
        }
        // If the element is not found, print an error message.
        output.writeLine("Group with id: \"" + inlineParams.get(1) + "\" not found" + "\n");
        return;

    }
}
