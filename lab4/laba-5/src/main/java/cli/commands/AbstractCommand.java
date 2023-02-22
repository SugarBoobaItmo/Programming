package cli.commands;

import java.util.List;

import collection_manager.AbstractManager;

public abstract class AbstractCommand {
    private String name;
    private String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute(List<String> inlineParams);
}
