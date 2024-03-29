package war;

import cli.CLIClient;
import cli.commands.ClearCommand;
import cli.commands.ExecuteScriptCommand;
import cli.commands.ExitCommand;
import cli.commands.FilterByTransferredStudentsCommand;
import cli.commands.FilterStartsWithNameCommand;
import cli.commands.GenerateGroupCommand;
import cli.commands.HelpCommand;
import cli.commands.HistoryCommand;
import cli.commands.InfoCommand;
import cli.commands.InsertCommand;
import cli.commands.PrintDescendingCommand;
import cli.commands.RemoveGreaterCommand;
import cli.commands.RemoveKeyCommand;
import cli.commands.RemoveLowerCommand;
import cli.commands.SaveCommand;
import cli.commands.ShowCommand;
import cli.commands.UpdateCommand;
import cli.terminal_commands.FuzzyCommand;
import cli.terminal_commands.LoadFileCommand;
import cli.terminal_commands.TerminalClear;
import cli.terminal_commands.TerminalHelp;
import collection_manager.AbstractManager;
import collection_manager.LocalManager;

public class App {
    public static void main(String[] args) {
            
            AbstractManager manager = new LocalManager(args);
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
            cli.registerCommand("help", new HelpCommand(cli));
            cli.registerCommand("execute_script", new ExecuteScriptCommand(cli));
            cli.registerCommand("history", new HistoryCommand(cli));
            cli.registerCommand("generate_group", new GenerateGroupCommand(manager));

            cli.registerTerminalCommand("/clear", new TerminalClear());
            cli.registerTerminalCommand("/help", new TerminalHelp(cli));
            cli.registerTerminalCommand("/fuzzyMode", new FuzzyCommand(cli));
            cli.registerTerminalCommand("/load", new LoadFileCommand(manager));


            cli.startCLI();
        
    }

}
