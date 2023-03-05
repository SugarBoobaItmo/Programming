package cli.terminal_commands;

import cli.CLIClient;

public class TerminalHelp extends TerminalCommand{
    private CLIClient cli;

    public TerminalHelp(CLIClient cli) {
        super("help", "Shows all available commands");
        this.cli = cli;
    }
    
    @Override
    public void execute() {
        System.out.println("Available terminal commands:");
        cli.getTerminalCommands().forEach((key, value) -> {
            System.out.println(key + " - " + value.getDescription());
        });
    }
    
}
