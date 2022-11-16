package src.abs;

import src.interfaces.Descriptable;

import java.util.Objects;

import src.classes.Point;

public abstract class Location extends Positioned implements Descriptable {
    protected String name;
    protected Point pos2;

    public Location(Point pos, String name, Point pos2) {
        super(pos);
        this.name = name;
        this.pos2 = pos2;
    }

    public String getName() {
        return name;
    }

    public Point getPosition2() {
        return pos2;
    }

    @Override
    public String toString() {
        return "Location " + this.describe();
    }

    @Override
    public boolean equals(Object other) {
        
        if (this == other) {
            return true;
        }
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Location otherLocation = (Location) other;
        return this.name.equals(otherLocation.name) && this.pos.equals(otherLocation.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pos, getClass());
    }

    public abstract String describe();

    public abstract boolean checkConsist(Positioned obj);
}
