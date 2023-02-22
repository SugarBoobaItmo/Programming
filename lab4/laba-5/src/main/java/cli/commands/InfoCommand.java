package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;

public class InfoCommand extends AbstractCollectionCommand {

    public InfoCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 1) {
            System.out.println(manager.getInfo().toString());
        } else {
            System.out.println("Incorrect command, please write it without parameters");
        }
    }

}
