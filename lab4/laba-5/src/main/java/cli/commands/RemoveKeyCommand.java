package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;

public class RemoveKeyCommand extends AbstractCollectionCommand {

    public RemoveKeyCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 2) {

            manager.removeKey(Integer.parseInt(inlineParams.get(1)));
        } else
            System.out.println("Incorrect command, please write it with correct parameters");
    }

}
