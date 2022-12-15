package src.classes;

import src.abs.Animal;
import src.abs.Creature;
import src.abs.Positioned;
import src.abs.Thing;
import src.enums.Liquids;
import src.enums.Properties;
import src.exceptions.IncorrectSizeException;
import src.interfaces.*;
import src.interfaces.Throwable;

public class Ru extends Animal implements Pushable, Flipable, Explainable, Swallowable, Throwable {
    public Ru(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "А я уже принял";
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
            if (this.getCreatureSize() > 1) {
                this.setCreatureSize(this.getCreatureSize() - 1);
            }
        } catch (IncorrectSizeException e) {

        }
    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 1;
    }

    @Override
    public void eat(String[] food) {
        for (String i: food){
            System.out.println("съел "+i);
        }
        this.setEnergy(getEnergy() + 20);
    }

    @Override
    public String swallow(Liquids liquid) {
        this.setEnergy(getEnergy()+10);
        return ("проглотил " + liquid + " не задумываясь");

    }

    @Override
    public String explain(Creature creature, String info) {
        return "объяснил " + creature.getName() + " " + info;
    }

    @Override
    public String flip(Thing thing, int quantity) {
        return " безжалостно уронил " + thing + " в количестве " + quantity + " шт.";
    }

    @Override
    public void push(Positioned obj) {

        if (obj instanceof Creature) {
            Creature creature = (Creature) obj;
            int x, y;
            x = this.getPosition().getX();
            y = this.getPosition().getY();
            creature.run(x + 6, y + 6);
            System.out.println(this.getName()+" толкнул "+ creature.getName()+" на 6 м." );
        }
    }

    @Override
    public void throwing(Thing thing, Creature creature) {

        if (creature.getEnergy() < 80) {
            int power;
            if (thing.getOwner().equals(this)) {
                power = 5;
            } else {
                power = 3;
            }

            int x, y;
            x = this.getPosition().getX();
            y = this.getPosition().getY();
            creature.run(x + power, y + power);
            System.out.println(this.getName()+" кинул "+ thing.getName() +" в "+ creature.getName()+" и тот отлетел на "+power+" м.");
        }
    }
}