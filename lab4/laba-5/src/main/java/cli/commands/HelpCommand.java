package cli.commands;

import java.util.List;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("help", "Write commands info");
    }

    @Override
    public void execute(List<String> inlineParams) {
        for (String i : inlineParams) {
            System.out.println(i);
        }

    }

}
