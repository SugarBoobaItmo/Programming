package cli.commands;

import cli.CLIClient;

/**
 * 
 * An abstract class representing a command that is supported by a CLI
 * 
 * This class extends the AbstractCommand class and adds a reference to a
 * CLIClient object.
 */
public abstract class CLISupportedCommand extends AbstractCommand {
    protected CLIClient cli;

    /**
     * 
     * Constructs a new CLISupportedCommand object with the given name, description,
     * and CLIClient.
     * 
     * @param name        The name of the command.
     * @param description The description of the command.
     * @param cli         The CLIClient associated with this command.
     */
    public CLISupportedCommand(String name, String description, CLIClient cli) {
        super(name, description);
        this.cli = cli;
    }

}
