package cli.commands;

import collection_manager.AbstractManager;

/**
 * 
 * The AbstractCollectionCommand class is an abstract class that represents a
 * command that operates on an AbstractManager object, which manages a
 * collection.
 * 
 * It is a relative for special commands that work with collection during script
 * execution (insert, remove, update).
 */
public abstract class AbstractCollectionCommand extends AbstractCommand {
    /**
     * 
     * The AbstractManager object that manages the collection.
     */
    protected AbstractManager manager;

    /**
     * 
     * Constructs an AbstractCollectionCommand object with a given name,
     * description, and AbstractManager object.
     * 
     * @param name        the name of the command
     * @param description the description of the command
     * @param manager     the AbstractManager object that manages the collection
     */
    public AbstractCollectionCommand(String name, String description, AbstractManager manager) {
        super(name, description);
        this.manager = manager;
    }
}
