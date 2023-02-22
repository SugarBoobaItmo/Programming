package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;

public class ExitCommand extends AbstractCommand {

    public ExitCommand(String name, String description) {
        super(name, description);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 1) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Incorrect command, please write it without parameters");
        }

    }

}
