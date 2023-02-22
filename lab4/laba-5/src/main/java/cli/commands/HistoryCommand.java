package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;

public class HistoryCommand extends AbstractCommand {

    public HistoryCommand(String name, String description) {
        super(name, description);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 0) {
            System.out.println("History is empty");
        } else if (inlineParams.size() < 7) {
            System.out.println("History:");
            inlineParams.forEach(System.out::println);
        } else {
            System.out.println("History:");
            List<String> sublist = inlineParams.subList(inlineParams.size() - 6, inlineParams.size());
            sublist.forEach(System.out::println);
        }
    }
}
