package cli.commands;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import durgaapi.Response;
import models.CollectionRecord;
import serveradapter.ServerAdapter;
import utils.ColorText;

/**
 * 
 * The ShowCommand class represents a command that displays the entire
 * collection of study groups.
 */
public class ShowCommand extends AbstractCollectionCommand {
    private ServerAdapter serverAdapter;
     /**
     * 
     * Constructs a new ShowCommand with the specified collection manager.
     * 
     * @param manager the collection manager to be used
     */
    public ShowCommand(ServerAdapter serverAdapter, AbstractManager manager) {
        super("Show", "Show collection", new ArrayList<String>(), manager);
        this.serverAdapter = serverAdapter;
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
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts) throws ExecuteError {
        Checkers.checkInlineParamsCount(0, inlineParams);
        try {
            Response response = serverAdapter.sendRequest("show", null);
            manager.setCollectionRecord((CollectionRecord) response.getData().get("object"));
            if (manager.getCollection() == null || manager.getCollection().size() == 0) {
                output.writeLine("Collection is empty" + "\n");
            } else {
                
                manager.getCollection().forEach((k, v) -> output.writeLine(ColorText.colorText(k + "", "green") + ColorText.colorText(": StudyGroup id=", "yellow")+ColorText.colorText(v.getId(), "green") + "\n"+
                ColorText.colorText("· name=", "yellow")+ ColorText.colorText(v.getName(), "green") + "\n"+
                ColorText.colorText("· coordinates=", "yellow")+ ColorText.colorText(v.getCoordinates().toString(), "green") + "\n"+
                ColorText.colorText("· creationDate=", "yellow")+ ColorText.colorText(v.getCreationDate().toString(), "green") + "\n"+
                ColorText.colorText("· studentsCount=", "yellow")+ ColorText.colorText(String.valueOf(v.getStudentsCount()), "green") + "\n"+
                ColorText.colorText("· expelledStudents=", "yellow")+ ColorText.colorText(String.valueOf(v.getExpelledStudents()), "green") + "\n"+
                ColorText.colorText("· transferredStudents=", "yellow")+ ColorText.colorText(String.valueOf(v.getTransferredStudents()), "green") + "\n"+         
                ColorText.colorText("· semesterEnum=", "yellow")+ ColorText.colorText(String.valueOf(v.getSemesterEnum()), "green") + "\n"+
                ColorText.colorText("· groupAdmin=", "yellow")+ ColorText.colorText(String.valueOf(v.getGroupAdmin()), "green") + "\n"+
                ColorText.colorText("· owner=", "yellow")+ ColorText.colorText(String.valueOf(v.getOwner()), "green") + "\n\n"));
            }
        } catch (UnknownHostException e) {
            throw new ExecuteError("Server is not available");
        } catch (IOException e) {
            throw new ExecuteError("Server is not available");
        }

    }
}
