package war;

import java.io.IOException;

import cli.CLIClient;
import cli.commands.ClearCommand;
import cli.commands.ExitCommand;
import cli.commands.FilterByTransferredStudentsCommand;
import cli.commands.FilterStartsWithNameCommand;
import cli.commands.InfoCommand;
import cli.commands.InsertCommand;
import cli.commands.PrintDescendingCommand;
import cli.commands.RemoveGreaterCommand;
import cli.commands.RemoveKeyCommand;
import cli.commands.RemoveLowerCommand;
import cli.commands.SaveCommand;
import cli.commands.ShowCommand;
import cli.commands.UpdateCommand;
import collection_manager.AbstractManager;
import collection_manager.LocalManager;


public class App {
    public static void main(String[] args) throws IOException {
        AbstractManager manager = new LocalManager("file.csv");

        CLIClient cli = new CLIClient(true);

        cli.registerCommand("save", new SaveCommand(manager));
        cli.registerCommand("info", new InfoCommand(manager));
        cli.registerCommand("show", new ShowCommand(manager));
        cli.registerCommand("clear", new ClearCommand(manager));
        cli.registerCommand("exit", new ExitCommand());
        cli.registerCommand("insert", new InsertCommand(manager));
        cli.registerCommand("print_descending", new PrintDescendingCommand(manager));
        cli.registerCommand("update", new UpdateCommand(manager));
        cli.registerCommand("remove_greater", new RemoveGreaterCommand(manager));
        cli.registerCommand("remove_lower", new RemoveLowerCommand(manager));
        cli.registerCommand("remove_key", new RemoveKeyCommand(manager));
        cli.registerCommand("filter_starts_with_name", new FilterStartsWithNameCommand(manager));
        cli.registerCommand("filter_by_transferred_students", new FilterByTransferredStudentsCommand(manager));

        cli.startCLI();
    }
}
