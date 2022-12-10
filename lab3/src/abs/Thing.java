package src.abs;

import src.interfaces.Descriptable;
import src.classes.Point;

public abstract class Thing extends Positioned implements Descriptable {

    private String name;
    private Creature owner;

    public Thing(Point pos, Creature owner, String name) {
        super(pos);
        this.owner = owner;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Creature getOwner() {
        return this.owner;
    }

    @Override
    public String describe() {
        return this.name + "- вещь " + this.owner;
    }
}
