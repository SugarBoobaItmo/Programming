package cli.commands;

import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.IncorrectInlineParamsCount;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

public class HistoryCommand extends CLISupportedCommand {
    public HistoryCommand(CLIClient cli) {
        super("History", "Show history of commands", cli);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output)
            throws IncorrectInlineParamsCount {

        Checkers.checkInlineParamsCount(0, inlineParams);
        List<String> commandsHistory = cli.getCommandsHistory();
        
        if (commandsHistory.size() == 0) {
            output.writeLine("History is empty" + "\n");
        } else if (commandsHistory.size() < 7) {
            output.writeLine("History:" + "\n");
            commandsHistory.forEach(v -> output.writeLine(v + "\n"));
        } else {
            output.writeLine("History:" + "\n");
            List<String> sublist = commandsHistory.subList(commandsHistory.size() - 6, commandsHistory.size());
            sublist.forEach(v -> output.writeLine(v + "\n"));
        }
    }
}
