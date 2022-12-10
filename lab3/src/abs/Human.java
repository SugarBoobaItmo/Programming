package src.abs;

import src.classes.Point;
import src.enums.Properties;

public abstract class Human extends Creature {
    public Human(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);

    }
}
