package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;

public class FilterByTransferredStudentsCommand extends AbstractCollectionCommand {

    public FilterByTransferredStudentsCommand(String name, String description, AbstractManager manager) {
        super(name, description, manager);
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 2 && inlineParams.get(1).matches("\\d+")) {

            for (int i = 0; i < manager.getCollection().size(); i++) {
                if (manager.getCollection().get(i).getTransferredStudents() == Integer.parseInt(inlineParams.get(1))) {
                    System.out.print("- ");
                    System.out.println(manager.getCollection().get(i));
                }
            }
        } else {
            System.out.println("Incorrect command, please write it without parameters");
        }
        // TODO Auto-generated method stub

    }

}
