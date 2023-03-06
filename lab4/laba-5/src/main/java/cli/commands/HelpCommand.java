package cli.commands;

import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.IncorrectInlineParamsCount;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

public class HelpCommand extends CLISupportedCommand {
    public HelpCommand(CLIClient cli) {
        super("help", "Write commands info", cli);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output)
            throws IncorrectInlineParamsCount {

        Checkers.checkInlineParamsCount(0, inlineParams);

        cli.getCommands().forEach((key, value) -> {
            output.writeLine(key + " - " + value.getDescription() + "\n");
        });
    }

}
