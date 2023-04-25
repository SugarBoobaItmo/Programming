package cli.commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import utils.ColorText;

/**
 * The PrintDescendingCommand class represents a command to print the collection
 * in descending order.
 */
public class PrintDescendingCommand extends AbstractCollectionCommand {
    /**
     * 
     * Constructor for the PrintDescendingCommand class.
     * 
     * @param manager An instance of the AbstractManager class.
     */
    public PrintDescendingCommand(AbstractManager manager) {
        super("Print_descending", "Print collection in descending order", new ArrayList<String>(), manager);
    }

    /**
     * 
     * Executes the command to print the collection in descending order.
     * 
     * @param inlineParams The list of parameters provided with the command.
     * @param input        The LineReader to read input from.
     * @param output       The LineWriter to write output to.
     * @throws ExecuteError If there is an error executing the command.
     */
    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        manager.getCollection()
                .values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .forEach((v) -> output.writeLine("-" + ColorText.colorText("StudyGroup id=", "yellow")+ColorText.colorText(v.getId(), "green") + "\n"+
                ColorText.colorText("· name=", "yellow")+ ColorText.colorText(v.getName(), "green") + "\n"+
                ColorText.colorText("· coordinates=", "yellow")+ ColorText.colorText(v.getCoordinates().toString(), "green") + "\n"+
                ColorText.colorText("· creationDate=", "yellow")+ ColorText.colorText(v.getCreationDate().toString(), "green") + "\n"+
                ColorText.colorText("· studentsCount=", "yellow")+ ColorText.colorText(String.valueOf(v.getStudentsCount()), "green") + "\n"+
                ColorText.colorText("· expelledStudents=", "yellow")+ ColorText.colorText(String.valueOf(v.getExpelledStudents()), "green") + "\n"+
                ColorText.colorText("· transferredStudents=", "yellow")+ ColorText.colorText(String.valueOf(v.getTransferredStudents()), "green") + "\n"+         
                ColorText.colorText("· semesterEnum=", "yellow")+ ColorText.colorText(String.valueOf(v.getSemesterEnum()), "green") + "\n"+
                ColorText.colorText("· groupAdmin=", "yellow")+ ColorText.colorText(String.valueOf(v.getGroupAdmin()), "green") + "\n\n"));
    }
}
