package src.abs;

import src.enums.Properties;
import src.exceptions.IncorrectSizeException;

import java.util.Objects;

import src.classes.Point;
import src.interfaces.*;
import src.interfaces.Runnable;

import java.util.Random;

public abstract class Creature extends Positioned
        implements Energed, Descriptable, Silent, Standing, Hearable, Runnable {
    private Properties[] personality;

    private String name;
    private int creatureSize;
    private int energy;

    public Creature(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos);
        this.name = name;
        this.creatureSize = creatureSize;
        this.energy = energy;
        this.personality = personality;
    }

    protected void setCreatureSize(int creatureSize) throws IncorrectSizeException {
        if (creatureSize < 150) {
            this.creatureSize = creatureSize;
        } else {
            throw new IncorrectSizeException(name + " достиг максимального размера");
        }
    }

    protected void setEnergy(int energy) {
        this.energy = energy;
    }

    protected void setProperties(Properties[] personality) {
        this.personality = personality;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getCreatureSize() {
        return creatureSize;
    }

    public int getEnergy() {
        return energy;
    }

    public Properties[] getProperties() {

        return personality.clone();
    }

    public String getName() {
        return name;

    }

    @Override
    public void run(int x, int y) {
        Point newPos = new Point(this.getPosition().getX() + x, this.getPosition().getY() + y,
                this.getPosition().getZ());
        this.setPosition(newPos);
    }

    @Override
    public String describe() {
        Random rand = new Random();
        Properties property = personality[rand.nextInt(personality.length)];
        return property.translation + " " + name + " и его размер " + creatureSize;
    }

    @Override
    public String sayNothing() {
        return "Просто молчит";
    }

    @Override
    public String stand() {

        return "Встал";

    }

    @Override
    public String hear(String obj) {
        return this.name + " услышал " + obj;
    }

    @Override
    public String toString() {
        return "Creature " + name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Creature otherCreature = (Creature) other;
        return name.equals(otherCreature.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getClass());
    }

    public abstract String say();

    public abstract void eat();

}
