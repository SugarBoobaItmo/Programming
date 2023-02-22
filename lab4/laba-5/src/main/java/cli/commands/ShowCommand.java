package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;
import models.StudyGroup;

public class ShowCommand extends AbstractCollectionCommand {

    public ShowCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {

        if (inlineParams.size() == 1) {
            if (manager.getCollection().size() == 0) {
                System.out.println("Collection is empty");

            } else {

                manager.getCollection().forEach((k, v) -> System.out.println("-" + v));

            }
        } else {
            System.out.println("Incorrect command, please write it without parameters");
        }

    }

}
