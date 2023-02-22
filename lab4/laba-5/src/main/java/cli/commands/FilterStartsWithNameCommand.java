package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;
import models.StudyGroup;

public class FilterStartsWithNameCommand extends AbstractCollectionCommand {

    public FilterStartsWithNameCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 2) {
            String substring = inlineParams.get(1);
            for (StudyGroup studyGroup : manager.getCollection().values()) {
                if (studyGroup.getName().startsWith(substring)) {
                    System.out.println("-" + studyGroup);
                }
            }
        } else
            System.out.println("Incorrect command, please write it with correct parameters");
    }

}
