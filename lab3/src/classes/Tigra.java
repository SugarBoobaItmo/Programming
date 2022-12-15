package src.classes;

import src.enums.Properties;
import src.exceptions.IncorrectSituationException;
import src.exceptions.IncorrectSizeException;
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
    public void work(int duration) throws IncorrectSizeException{
        if ((this.getEnergy() - duration) > 0) {
            this.setEnergy(this.getEnergy() - duration);
        } else {
            this.setEnergy(0);
        }
        try {
            if (this.getCreatureSize() > 10) {
                this.setCreatureSize(this.getCreatureSize() - 10);
            }
        } catch (IncorrectSizeException e) {

        }
    }

    @Override
    public boolean isTired(){
        if (getEnergy() < 1) {
            return true;
        }
        throw new IncorrectSituationException("Невозможная ситуация");

    }

    @Override
    public void eat(String[] food){
        for (String i: food){
            System.out.println("съел "+i);
        }
        this.setEnergy(getEnergy() + 5);
        try {

            this.setCreatureSize(getCreatureSize()+5);
        } catch (IncorrectSizeException e){
            System.out.println(e.getMessage());
            System.out.println(this.getName() + " немного переел");
        }
    }

    @Override
    public String swallow(Liquids liquid) {
        this.setEnergy(getEnergy()+12);
        return ("проглотил " + liquid + " не задумываясь");

    }

    @Override
    public String explain(Creature creature, String info) {
        return "объяснил " + creature.getName() + " " + info;
    }

    @Override
    public String flip(Thing thing, int quantity) {
        return this.getName()+" безжалостно уронил " + thing + " в количестве " + quantity + " шт.";
    }

    @Override
    public void push(Positioned obj) {

        if (obj instanceof Creature) {
            Creature creature = (Creature) obj;
            int x, y;
            x = this.getPosition().getX();
            y = this.getPosition().getY();
            creature.run(x + 1, y + 1);
            System.out.println(this.getName()+" толкнул "+ creature.getName()+" на 1 м." );

        }

    }

    @Override
    public void throwing(Thing thing, Creature creature) {
        if (creature.getEnergy() < 50) {
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
            System.out.println(this.getName()+" кинул "+ thing.getName() +" в "+ creature.getName()+" и тот отлетел на "+power+" м.");

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
