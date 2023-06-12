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
import utils.ColorText;

/**
 * 
 * The FilterStartsWithNameCommand class represents a command that filters the
 * collection by given name.
 * 
 */
public class FilterStartsWithNameCommand extends AbstractCollectionCommand {

    /**
     * 
     * Constructs a new FilterStartsWithNameCommand with the specified collection
     * manager.
     * 
     * @param manager the collection manager to be used
     */
    public FilterStartsWithNameCommand(AbstractManager manager) {
        super("FilterStartsWithName", "Filter by name", new ArrayList<String>(Arrays.asList("name")), manager);
    }

    /**
     * 
     * Executes the FilterStartsWithNameCommand by filtering the collection by given
     * name.
     * 
     * @param inlineParams the list of parameters passed inline with the command
     * @param input        the LineReader used to read input from the console (not
     *                     used in this method)
     * @param output       the LineWriter used to write output to the console
     * @throws ExecuteError if an error occurs while executing the command
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);

        // given name
        String substring = inlineParams.get(1);
        // filter the collection by given name
        for (StudyGroup v : manager.getCollection().values()) {
            if (v.getName().startsWith(substring)) {
                output.writeLine(ColorText.colorText("- StudyGroup id=", "yellow")+ColorText.colorText(v.getId(), "green") + "\n"+
                ColorText.colorText("· name=", "yellow")+ ColorText.colorText(v.getName(), "green") + "\n"+
                ColorText.colorText("· coordinates=", "yellow")+ ColorText.colorText(v.getCoordinates().toString(), "green") + "\n"+
                ColorText.colorText("· creationDate=", "yellow")+ ColorText.colorText(v.getCreationDate().toString(), "green") + "\n"+
                ColorText.colorText("· studentsCount=", "yellow")+ ColorText.colorText(String.valueOf(v.getStudentsCount()), "green") + "\n"+
                ColorText.colorText("· expelledStudents=", "yellow")+ ColorText.colorText(String.valueOf(v.getExpelledStudents()), "green") + "\n"+
                ColorText.colorText("· transferredStudents=", "yellow")+ ColorText.colorText(String.valueOf(v.getTransferredStudents()), "green") + "\n"+         
                ColorText.colorText("· semesterEnum=", "yellow")+ ColorText.colorText(String.valueOf(v.getSemesterEnum()), "green") + "\n"+
                ColorText.colorText("· groupAdmin=", "yellow")+ ColorText.colorText(String.valueOf(v.getGroupAdmin()), "green") + "\n\n");
            }
        }
    }
}
