package war;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import cli.CLIClient;
import cli.commands.ClearCommand;
import cli.commands.ExecuteScriptCommand;
import cli.commands.ExitCommand;
import cli.commands.FilterByTransferredStudentsCommand;
import cli.commands.FilterStartsWithNameCommand;
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
import collection_manager.AbstractManager;
import collection_manager.LocalManager;

// import javafx.scene.paint.Color;
import models.Color;
import models.Coordinates;
import models.Person;
import models.Semester;
import models.StudyGroup;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {   
        // System.out.println(Long.parseLong(""));

        // AbstractManager manager = new LocalManager("file.csv");
        AbstractManager manager = new LocalManager("file.csv");

        CLIClient cli = new CLIClient();


        cli.registerCommand("save", new SaveCommand("Save", "Save collection to file", manager));
        cli.registerCommand("help", new HelpCommand());
        cli.registerCommand("info", new InfoCommand("Info", "Write info about collection", manager));
        cli.registerCommand("show", new ShowCommand("Show", "Show collection", manager));
        cli.registerCommand("clear", new ClearCommand("Clear", "Clear collection", manager));
        cli.registerCommand("exit", new ExitCommand("Exit", "Exit from program"));
        cli.registerCommand("history", new HistoryCommand("History", "Show history of commands"));
        cli.registerCommand("filter_by_transfered_students", new FilterByTransferredStudentsCommand("Filter_by_transferred_students" ,"Filter by number of transferred students", manager));
        cli.registerCommand("insert", new InsertCommand("Insert", "Insert element to collection", manager));
        cli.registerCommand("print_descending", new PrintDescendingCommand("Print_descending", "Print collection in descending order", manager));
        cli.registerCommand("update", new UpdateCommand("Update", "Update element of collection by ID", manager));
        cli.registerCommand("remove_greater", new RemoveGreaterCommand("RemoveGreater", "Remove elements which are greater than given", manager));
        cli.registerCommand("remove_lower", new RemoveLowerCommand("RemoveLower", "Remove elements which are lower than given", manager));
        cli.registerCommand("remove_key", new RemoveKeyCommand("RemoveKey", "Remove element by key", manager));
        cli.registerCommand("filter_starts_with_name", new FilterStartsWithNameCommand("FilterStartsWithName", "Filter by name", manager));
        cli.registerCommand("execute_script", new ExecuteScriptCommand("ExecuteScript", "Execute script from file", cli::acceptLine));
        // String[] args1 = s.split(" ");
        // System.out.println(args1[0]);
        // cli.executeCommand(s, args1);
        cli.startCLI();
        // Save save = new Save(manager.getCollection());
        // manager.save("file.csv");




        
        

    }
}
