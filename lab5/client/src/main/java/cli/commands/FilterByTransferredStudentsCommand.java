package cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.StudyGroup;
import utils.ColorText;

/**
 * 
 * The FilterByTransferredStudentsCommand class represents a command that filters
 * the collection by the given number of transferred students.
 * 
 */
public class FilterByTransferredStudentsCommand extends AbstractCollectionCommand {

    /**
     * 
     * Constructs a new FilterByTransferredStudentsCommand with the specified
     * collection manager.
     * 
     * @param manager the collection manager to be used
     */
    public FilterByTransferredStudentsCommand(AbstractManager manager) {
        super("Filter_by_transferred_students" ,"Filter by number of transferred students", new ArrayList<String>(Arrays.asList("transferredStudents ")), manager);
    }

    /**
     * 
     * Executes the FilterByTransferredStudentsCommand by filtering the collection by
     * the given number of transferred students.
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
        Checkers.checkLong(inlineParams.get(1));

        TreeMap<String, StudyGroup> collection = manager.getCollection();
        // filter the collection by the given number of transferred students
        for (String v : collection.keySet()) {
            // get the number of transferred students
            long transferredStudentsCount = collection.get(v).getTransferredStudents();
            // get the expected (given) number of transferred students
            long expectedCount = Long.parseLong(inlineParams.get(1));
            if (transferredStudentsCount == expectedCount) {
                // output.writeLine("- " + "\n");
                StudyGroup currentGroup = collection.get(v); 
                output.writeLine(ColorText.colorText("- StudyGroup id=", "yellow")+ColorText.colorText(currentGroup.getId(), "green") + "\n"+
                ColorText.colorText("· name=", "yellow")+ ColorText.colorText(currentGroup.getName(), "green") + "\n"+
                ColorText.colorText("· coordinates=", "yellow")+ ColorText.colorText(currentGroup.getCoordinates().toString(), "green") + "\n"+
                ColorText.colorText("· creationDate=", "yellow")+ ColorText.colorText(currentGroup.getCreationDate().toString(), "green") + "\n"+
                ColorText.colorText("· studentsCount=", "yellow")+ ColorText.colorText(String.valueOf(currentGroup.getStudentsCount()), "green") + "\n"+
                ColorText.colorText("· expelledStudents=", "yellow")+ ColorText.colorText(String.valueOf(currentGroup.getExpelledStudents()), "green") + "\n"+
                ColorText.colorText("· transferredStudents=", "yellow")+ ColorText.colorText(String.valueOf(currentGroup.getTransferredStudents()), "green") + "\n"+         
                ColorText.colorText("· semesterEnum=", "yellow")+ ColorText.colorText(String.valueOf(currentGroup.getSemesterEnum()), "green") + "\n"+
                ColorText.colorText("· groupAdmin=", "yellow")+ ColorText.colorText(String.valueOf(currentGroup.getGroupAdmin()), "green") + "\n\n");
            }
        }
    }
}
