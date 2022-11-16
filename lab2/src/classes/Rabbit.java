package src.classes;

import src.abs.Creature;
import src.enums.Properties;
import src.interfaces.Runnable;

public class Rabbit extends Creature implements Runnable {
    public Rabbit(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "Теперь я знаю, где мы!";
    }

    @Override
    public void relax(int duration) {
        energy += duration + 10;
    }

    @Override
    public void work(int duration) {
        if ((energy - duration) >= 3) {
            energy -= duration;
        } else {
            energy = 0;
        }
    }

    @Override
    public boolean isTired() {
        return energy < 3;
    }

    @Override
    public void run(int x, int y) {
        Point newPos = new Point(pos.x + x, pos.y + y, pos.z);
        pos = newPos;
    }
}
