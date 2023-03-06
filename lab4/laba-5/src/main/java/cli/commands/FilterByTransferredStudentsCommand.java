package cli.commands;

import java.util.List;
import java.util.TreeMap;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import collection_manager.AbstractManager;
import models.StudyGroup;

public class FilterByTransferredStudentsCommand extends AbstractCollectionCommand {

    public FilterByTransferredStudentsCommand(AbstractManager manager) {
        super("Filter_by_transferred_students" ,"Filter by number of transferred students", manager);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output) throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);
        Checkers.checkLong(inlineParams.get(1));

        TreeMap<String, StudyGroup> collection = manager.getCollection();
        for (String v : collection.keySet()) {
            long transferredStudentsCount = collection.get(v).getTransferredStudents();
            long expectedCount = Long.parseLong(inlineParams.get(1));
            if (transferredStudentsCount == expectedCount) {
                output.writeLine("- " + "\n");
                StudyGroup currentGroup = collection.get(v); 
                output.writeLine(currentGroup.toString() + "\n");
            }
        }
    }
}
