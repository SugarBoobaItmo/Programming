package src.abs;
import src.classes.Point;

public abstract class Positioned {
    protected Point pos;

    public Positioned(Point pos){
        this.pos = pos;
    }
    
    public Point getPosition(){
        return pos;
    } 

}
