package src.classes;

import src.abs.Container;
import src.abs.Creature;
import src.abs.Positioned;
import src.abs.Thing;
import src.enums.Liquids;
import src.interfaces.Pushable;

public class Pot extends Container implements Pushable{


    public Pot(Point pos, Creature owner, String name, Liquids liquid) {
        super(pos, owner, name, liquid);
    }

    @Override
    public void push(Positioned obj) {
        if (obj instanceof Pot) {
            System.out.print(getName()+" толкнул "+ ((Thing) obj).getName()+" ");
            System.out.println("Bonk");
        } else System.out.println("Chponk");
        
    }

}
