package cli.commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import collection_manager.AbstractManager;
import models.StudyGroup;

public class PrintDescendingCommand extends AbstractCollectionCommand {
    
    public PrintDescendingCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 1) {
            manager.getCollection().values().stream().sorted(Comparator.reverseOrder())
                    .forEach((studyGroup) -> System.out.println("-" + studyGroup));
        } else {
            System.out.println("Incorrect command, please write it without parameters");
        }
    }
}
