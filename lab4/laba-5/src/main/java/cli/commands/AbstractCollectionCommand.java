package cli.commands;

import collection_manager.AbstractManager;

public abstract class AbstractCollectionCommand extends AbstractCommand {
    protected AbstractManager manager;

    public AbstractCollectionCommand(String name, String description, AbstractManager manager) {
        super(name, description);
        this.manager = manager;
    }
}
