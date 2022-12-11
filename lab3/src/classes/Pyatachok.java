package src.classes;

import src.abs.Animal;
import src.abs.Creature;
import src.enums.Properties;
import src.interfaces.Beepable;
import src.interfaces.Understandible;
import src.interfaces.Waiting;

public class Pyatachok extends Animal implements Waiting, Beepable, Understandible {
    public Pyatachok(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    private boolean confidance = false;

    public boolean getConfidance() {
        return confidance;
    }

    @Override
    public String say() {
        return "Помогите, спасите!";
    }

    @Override
    public void relax(int duration) {
        this.setEnergy(this.getEnergy() + duration);
    }

    @Override
    public void work(int duration) {
        if ((this.getEnergy() - duration * 3) >= 0) {
            this.setEnergy(this.getEnergy() - duration * 3);
        } else {
            this.setEnergy(0);
        }
        try {
            if (this.getCreatureSize() > 1) {
                this.setCreatureSize(this.getCreatureSize() - 1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible state");
        }
    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 50;
    }

    @Override
    public String waiting(int duration, Creature creature) {
        return getName() + " ждет " + creature.getName() + " " + duration + " минут";
    }

    @Override
    public void beep() {
        confidance = true;
    }

    @Override
    public String understood(String info) {
        return "понял "+ info; 
    }

    @Override
    public void eat(String[] food) {
        for (String i: food){
            System.out.println("съел "+i);
        }
        if (confidance) {
            this.setEnergy(getEnergy() + 9);
        } else
            this.setEnergy(getEnergy() + 3);
    }
}
