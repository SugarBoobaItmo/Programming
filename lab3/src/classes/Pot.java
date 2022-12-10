package src.classes;

import src.abs.Creature;
import src.abs.Positioned;
import src.abs.Thing;
import src.interfaces.Pushable;

public class Pot extends Thing implements Pushable {

    public Pot(Point pos, Creature owner, String name) {
        super(pos, owner, name);
    }

    @Override
    public void push(Positioned obj) {
        if (obj instanceof Pot) {
            System.out.println("Bonk");
        }
        System.out.println("Chponk");
    }

}
