package cli.terminal_commands;

import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import utils.ColorText;

/**
 * 
 * The TerminalHelp class is a class that displays all available commands in a
 * CLI application.
 * 
 * It contains a reference to the CLIClient class to access the list of
 * available terminal commands.
 */
public class TerminalHelp extends TerminalCommand {

    private CLIClient cli;

    /**
     * 
     * Constructor for the TerminalHelp class.
     * 
     * @param cli the CLIClient instance
     */
    public TerminalHelp(CLIClient cli) {
        super("/help", "Shows all available commands");
        this.cli = cli;
    }

    /**
     * 
     * Executes the help command by displaying a list of all available terminal
     * commands and their descriptions.
     */
    @Override
    public void execute(List<String> params) throws ExecuteError{
        
        Checkers.checkInlineParamsCount(0, params);
        System.out.println("Available terminal commands:");
        cli.getTerminalCommands().forEach((key, value) -> {
            System.out.println(String.format("\t%s\n\t\t%s\n\n", ColorText.colorText(key, "yellow")  ,  ColorText.colorText(value.getDescription(), "green")));
        });
    }

}
