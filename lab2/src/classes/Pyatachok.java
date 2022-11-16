package src.classes;

import src.abs.Creature;
import src.enums.Properties;

public class Pyatachok extends Creature {
    public Pyatachok(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "Помогите, спасите!";
    }

    @Override
    public void relax(int duration) {
        energy += duration;
    }

    @Override
    public void work(int duration) {
        if ((energy - duration * 3) >= 0) {
            energy -= duration * 3;
        } else {
            energy = 0;
        }
        if (this.creatureSize > 1) {
            this.creatureSize -= 1;
        }
    }

    @Override
    public boolean isTired() {
        return energy < 50;
    }
}
