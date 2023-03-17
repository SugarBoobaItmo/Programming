package cli.terminal_commands;

import cli.CLIClient;

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
        super("help", "Shows all available commands");
        this.cli = cli;
    }

    /**
     * 
     * Executes the help command by displaying a list of all available terminal
     * commands and their descriptions.
     */
    @Override
    public void execute() {
        System.out.println("Available terminal commands:");
        cli.getTerminalCommands().forEach((key, value) -> {
            System.out.println(key + " - " + value.getDescription());
        });
    }

}
