package cli.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cli.CLIClient;
import cli.commands.checker.Checkers;
import cli.commands.exceptions.ExecuteError;
import cli.exceptions.CommandNotFound;
import cli.interfaces.LineReader;
import cli.interfaces.LineWriter;
import utils.ColorText;

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
        super("ExecuteScript", "Execute script from file", new ArrayList<String>(Arrays.asList("file_name")), cli);
    }

    @Override
    public void execute(List<String> inlineParams, LineReader input, LineWriter output, boolean disableAttempts)
            throws ExecuteError {
        Checkers.checkInlineParamsCount(1, inlineParams);

        // get absolute path of script file
        String filePath = Paths.get(inlineParams.get(1)).normalize().toAbsolutePath().toString();

        // set or increase recursion depth and check for max depth
        int depth = scriptExecuteDepth.containsKey(filePath) ? scriptExecuteDepth.get(filePath) : 0;
        scriptExecuteDepth.put(filePath, depth + 1);
        if (depth + 1 >= maxDepth) {
            scriptExecuteDepth.remove(filePath);
            throw new ExecuteError("Max depth of script execution reached");
        }
        try {
            // read script file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean skipDepthReset = false;
            while ((line = reader.readLine()) != null) {

                // checking for max depth of script execution (if during execution of script
                // same script was invoked)
                if (!scriptExecuteDepth.containsKey(filePath) || scriptExecuteDepth.get(filePath) >= maxDepth) {
                    skipDepthReset = true;
                    break;
                }

                List<String> params = cli.parseParams(line);
                try {
                    AbstractCommand command = cli.resolveCommand(params);

                    reader.mark(100);
                    line = reader.readLine();
                    
                    if (ElementCommand.class.isAssignableFrom(command.getClass()) && line!=null) {

                        try {
                            List<String> next_line_params = cli.parseParams(line);
                            reader.reset();
                            cli.resolveCommand(next_line_params);
                            cli.executeCommand(params, command, input, output, false);                            
                        } catch (CommandNotFound e) {
                            SafeReadLine safeReadLine = new SafeReadLine(reader, output);

                            cli.executeCommand(params, command, safeReadLine::readLine, output, true);

                        }

                    } else {
                        reader.reset();
                        cli.executeCommand(params, command, input, output, false);
                    }

                } catch (CommandNotFound e) {
                    output.writeLine(ColorText.colorText("Command not found: " + e.getMessage() + "\n", "red"));
                } 

            }
            // if script exceeded recursion depth, don't reset depth
            // so in next script invocation it will be checked and exception will be thrown
            if (!skipDepthReset) {
                if (scriptExecuteDepth.containsKey(filePath)) {
                    depth = scriptExecuteDepth.get(filePath);
                    scriptExecuteDepth.put(filePath, depth - 1);
                }
            }
        output.writeLine("Script execution finished"+"\n");
        
        } catch (IOException e) {
            output.writeLine(ColorText.colorText("Error reading script file" + "\n", "red"));
        }
    }

    public class SafeReadLine {
        BufferedReader reader;
        LineWriter output;

        public SafeReadLine(BufferedReader reader, LineWriter output) {
            this.reader = reader;
            this.output = output;
        }

        public String readLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                output.writeLine(ColorText.colorText("Error reading script file" + "\n", "red"));
            }
            return null;
        }

    }

}