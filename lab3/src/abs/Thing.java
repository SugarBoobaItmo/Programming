package src.abs;

import src.interfaces.Descriptable;

import java.util.Objects;

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
    @Override
    public String toString() {
        return "Thing " + name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Thing otherThing = (Thing) other;
        return name.equals(otherThing.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getClass());
    }

}

