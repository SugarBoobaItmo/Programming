package cli.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.commands.exceptions.ScriptGroupIncorrectParams;
import cli.exceptions.CommandNotFound;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;

/**
 * 
 * The ExecuteScriptCommand class represents a command that executes a script
 * from a file.
 * 
 */
public class ExecuteScriptCommand extends CLISupportedCommand {
    // max depth of script execution (for recursion)
    private int maxDepth = 5;
    // map of script file paths and their execution depth
    private static HashMap<String, Integer> scriptExecuteDepth = new HashMap<String, Integer>();

    /**
     * 
     * Constructs a new ExecuteScriptCommand with the specified CLI client.
     * 
     * @param cli the CLI client to be used
     */
    public ExecuteScriptCommand(CLIClient cli) {
        super("ExecuteScript", "Execute script from file", cli);

    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output)
            throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);

        // get absolute path of script file
        String filePath = Paths.get(inlineParams.get(1)).normalize().toAbsolutePath().toString();
        // set or increase recursion depth and check for max depth
        if (scriptExecuteDepth.containsKey(filePath)) {
            int depth = scriptExecuteDepth.get(filePath);
            scriptExecuteDepth.put(filePath, depth + 1);
            if (depth + 1 >= maxDepth) {
                scriptExecuteDepth.remove(filePath);
                throw new ExecuteError("Max depth of script execution reached");
            }
        } else {
            scriptExecuteDepth.put(filePath, 1);
        }
        // read script file
        String[] fileLines = scriptReader(filePath);
        boolean skipDepthReset = false;
        if (fileLines != null) {
            for (int line = 0; line < fileLines.length; line++) {
                // checking for max depth of script execution (if during execution of script
                // same script was invoked)
                if (scriptExecuteDepth.get(filePath) >= maxDepth) {
                    skipDepthReset = true;
                    break;
                }
                // parse script file lines
                ArrayList<String> params = cli.parseParams(fileLines[line]);
                try {
                    AbstractCommand command = cli.resolveCommand(params);
                    // check for script group manipulation commands
                    if ((command.getName().equals("Insert")
                            || command.getName().equals("Update")
                            || command.getName().equals("RemoveGreater")
                            || command.getName().equals("RemoveLower"))
                            && line + 1 < fileLines.length
                            && fileLines[line + 1].matches("\\s+.*")) {
                        try {
                            // checking Insert fields for script group manipulation commands
                            ArrayList<String> insertParams = checkInsertFields(fileLines, line);
                            // add Insert fields to params list
                            insertParams.forEach(v -> params.add(v));
                            // skip next line (Insert fields)
                            line += insertParams.size();
                        } catch (ScriptGroupIncorrectParams e) {
                            System.out.println(e.getMessage());
                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Script group incorrect params");
                            break;
                        }
                    }
                    cli.executeCommand(params, command, input, output);
                } catch (CommandNotFound e) {
                    System.out.println("Command not found: " + e.getMessage());
                }
            }
        }
        // if script exceeded recursion depth, don't reset depth
        // so in next script invocation it will be checked and exception will be thrown
        if (!skipDepthReset && scriptExecuteDepth.containsKey(filePath)) {
            scriptExecuteDepth.remove(filePath);
        }
    }

    /**
     * 
     * Checks Insert fields for script group manipulation commands.
     * 
     * @param fileLines the script file lines
     * @param line      the line number
     * @return the list of Insert fields
     * @throws ScriptGroupIncorrectParams if the script group is incorrect
     */
    public ArrayList<String> checkInsertFields(String[] fileLines, int line) throws ScriptGroupIncorrectParams {
        ArrayList<String> insertFields = new ArrayList<>();
        // checking for 8 fields
        // if field with admin name is empty insert next 8 fields(checking if they
        // correspond to "\tab+word" format)
        if (fileLines[line + 8].matches("\\s+")) {
            for (int elem = line + 1; elem < line + 9; elem++) {
                if (fileLines[elem].matches("\\s+.+")) {
                    insertFields.add(fileLines[elem].trim());
                } else
                    throw new ScriptGroupIncorrectParams();
            }
            return insertFields;
        }
        // checking for 10 fields
        for (int i = line + 1; i < line + 11; i++) {

            if (fileLines[i].matches("\\s+.+")) {
                insertFields.add(fileLines[i].trim());
            } else {
                throw new ScriptGroupIncorrectParams();
            }
        }
        return insertFields;

    }

    /**
     * 
     * Reads a script file.
     * 
     * @param filePath the path to the script file
     * @return the script String array file lines
     */
    public String[] scriptReader(String filePath) {
        // read script file using InputStreamReader
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
            int t;
            StringBuilder sb = new StringBuilder();

            while ((t = reader.read()) != -1) {
                char r = (char) t;
                sb.append(r);

            }

            return sb.toString().split(System.lineSeparator());

        } catch (IOException e) {
            System.out.println("File not found or no permission to read it");
            return null;
        }
    }

}