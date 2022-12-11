package src.abs;

import src.abs.Thing;
import src.classes.Point;
import src.enums.Liquids;

public abstract class Container extends Thing{

    public Container(Point pos, Creature owner, String name, Liquids liquid) {
        super(pos, owner, name);
        this.liquid = liquid;
    }
    
    private Liquids liquid;

    public void setContent(Liquids liquid){
        this.liquid = liquid;
    }

    public Liquids getContent(){
        return liquid;
    }
}
