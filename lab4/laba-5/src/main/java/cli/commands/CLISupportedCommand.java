package cli.commands;

import cli.CLIClient;

public abstract class CLISupportedCommand extends AbstractCommand{
    protected CLIClient cli;

    public CLISupportedCommand(String name, String description, CLIClient cli) {
        super(name, description);
        this.cli = cli;
    }

    
    
}
