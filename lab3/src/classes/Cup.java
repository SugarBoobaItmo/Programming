package src.classes;

import src.abs.Container;
import src.abs.Creature;
import src.abs.Thing;
import src.enums.Liquids;

public class Cup extends Container{

    
    public Cup(Point pos, Creature owner, String name, Liquids liquid) {
        super(pos, owner, name, liquid);
    }
    
}
