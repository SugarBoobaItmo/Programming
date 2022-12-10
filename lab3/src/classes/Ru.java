package src.classes;

import src.abs.Animal;
import src.abs.Creature;
import src.abs.Positioned;
import src.abs.Thing;
import src.enums.Liquids;
import src.enums.Properties;
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
        this.setEnergy(this.getEnergy()+duration+15);
    }

    @Override
    public void work(int duration) {
        if ((this.getEnergy() - duration) > 0) {
            this.setEnergy(this.getEnergy()-duration);
        } else {
            this.setEnergy(0);
        }
        try {
            if (this.getCreatureSize() > 1) {
                this.setCreatureSize(this.getCreatureSize()-1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible state");
        }
    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 1; 
    }
    
    @Override 
    public void eat(){
        this.setEnergy(getEnergy()+20);
    }

    @Override
    public void swallow(Liquids liquid) {
        System.out.println("проглотил "+ liquid + "не задумываясь");
        
    }

    @Override
    public String explain(Creature creature, String info) {
        return "объяснил "+ creature+ " "+ info;
    }

    @Override
    public String flip(Thing thing, int quantity) {
        return "безжалостно уронил "+thing+" в количестве "+quantity+" шт.";
    }

    @Override
    public void push(Positioned obj) {

        if (obj instanceof Creature) {
            Creature creature = (Creature) obj;
            creature.setEnergy(getEnergy()-1);
        }
        
    }

    @Override
    public void throwing(Thing thing, Creature creature) {
        if (thing.getOwner().equals(this)){
            if (creature.getEnergy()>10){

                creature.setEnergy(getEnergy()-10);
            } else creature.setEnergy(0);
        } else creature.setEnergy(getEnergy()-1);
        
    }


}