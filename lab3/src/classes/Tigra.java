package src.classes;

import src.enums.Properties;
import src.exceptions.IncorrectSituationException;
import src.abs.Animal;
import src.abs.Creature;
import src.abs.Positioned;
import src.abs.Thing;
import src.enums.Liquids;
import src.interfaces.*;
import src.interfaces.Throwable;

public class Tigra extends Animal
        implements Pushable, Flipable, Explainable, Swallowable, Convertable, Growlable, Throwable {
    public Tigra(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "И я тоже";
    }

    @Override
    public void relax(int duration) {
        this.setEnergy(this.getEnergy() + duration + 15);
    }

    @Override
    public void work(int duration) {
        if ((this.getEnergy() - duration) > 0) {
            this.setEnergy(this.getEnergy() - duration);
        } else {
            this.setEnergy(0);
        }
        try {
            if (this.getCreatureSize() > 10) {
                this.setCreatureSize(this.getCreatureSize() - 10);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible state");
        }
    }

    @Override
    public boolean isTired() throws IncorrectSituationException {
        if (getEnergy() < 1) {
            return true;
        }
        throw new IncorrectSituationException("Невозможная ситуация");

    }

    @Override
    public void eat() {
        this.setEnergy(getEnergy() + 5);
    }

    @Override
    public void swallow(Liquids liquid) {
        System.out.println("проглотил " + liquid + "не задумываясь");

    }

    @Override
    public String explain(Creature creature, String info) {
        return "объяснил " + creature + " " + info;
    }

    @Override
    public String flip(Thing thing, int quantity) {
        return "безжалостно уронил " + thing + " в количестве " + quantity + " шт.";
    }

    @Override
    public void push(Positioned obj) {

        if (obj instanceof Creature) {
            Creature creature = (Creature) obj;
            int x, y;
            x = this.getPosition().getX();
            y = this.getPosition().getY();
            creature.run(x + 1, y + 1);
        }

    }

    @Override
    public void throwing(Thing thing, Creature creature) {
        if (creature.getEnergy() < 10) {
            int power;
            if (thing.getOwner().equals(this)) {
                power = 3;
            } else {
                power = 1;
            }

            int x, y;
            x = this.getPosition().getX();
            y = this.getPosition().getY();
            creature.run(x + power, y + power);
        }
    }

    @Override
    public String grow() {
        return "Громко рыкнуул";
    }

    @Override
    public void convert(Properties[] newProperties) {
        this.setProperties(newProperties);

    }

}
