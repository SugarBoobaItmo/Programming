package cli.commands;

import java.util.List;
import java.util.function.Consumer;

import cli.scriptutils.ScriptToRecord;

public class ExecuteScriptCommand extends AbstractCommand {
    Consumer<String> commandEvaluator;

    public ExecuteScriptCommand(String name, String description, Consumer<String> commandEvaluator) {
        super(name, description);
        this.commandEvaluator = commandEvaluator;
    }

    @Override
    public void execute(List<String> inlineParams) {
        if (inlineParams.size() == 2){
            if (ScriptToRecord.readLines(inlineParams.get(1))==null){
                // System.out.println(11111111);
                return;
            }
            System.out.println("Evaluate script .................");
            String[] commands = ScriptToRecord.readLines(inlineParams.get(1));
            for (String command : commands) {
                if (command.contains("execute_script")) {
                continue;}
                this.commandEvaluator.accept(command);
                
            }
            
            System.out.println(".... script done");
        } else {
            System.out.println("Incorrect command, please write it with one parameter");
            return;
        }
        
        // read file with script
        // iterate each line
        // check if not "script in line"
        // call evaluator with this string

        // this.commandEvaluator.accept(inlineParams);
    }

}
