package src.classes;

import src.abs.Creature;
import src.enums.Properties;
import src.interfaces.Waiting;

public class Pyatachok extends Creature implements Waiting {
    public Pyatachok(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
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
        if (this.getCreatureSize() > 1) {
            this.setCreatureSize(this.getCreatureSize() - 1);
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
}
