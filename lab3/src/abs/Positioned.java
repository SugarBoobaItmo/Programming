package src.abs;
import src.classes.Point;

public abstract class Positioned {
    private Point pos;

    public Positioned(Point pos){
        this.pos = pos;
    }
    
    public Point getPosition(){
        return pos;
    } 

    public void setPosition(Point pos){
        this.pos = pos;
    } 
}
