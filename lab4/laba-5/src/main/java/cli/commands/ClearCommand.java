package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;

public class ClearCommand extends AbstractCollectionCommand {
    public ClearCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 1) {
            this.manager.clear();
        } else {
            System.out.println("Incorrect command, please write it without parameters");
        }

    }

}
