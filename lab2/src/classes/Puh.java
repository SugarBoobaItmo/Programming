package src.classes;

import src.abs.Creature;
import src.enums.Properties;

public class Puh extends Creature {
    public Puh(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "Я тоже";
    }

    @Override
    public void relax(int duration) {
        energy += duration + 1;
        creatureSize += duration;
    }

    @Override
    public void work(int duration) {
        if ((energy - duration * 2) >= 0) {
            energy -= duration * 2;
        } else {
            energy = 0;
        }
        if (this.creatureSize > 1) {
            this.creatureSize -= 1;
        }
    }

    @Override
    public boolean isTired() {
        return energy < 10;
    }
}
