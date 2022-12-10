package src.classes;

import src.abs.Location;
import src.abs.Positioned;
import src.interfaces.Runnable;

public class Pit extends Location implements Runnable {
    public Pit(Point pos, String name, Point pos2) {
        super(pos, name, pos2);
    }

    public String describe() {
        return "Локация " + this.getName();
    }

    public boolean checkConsist(Positioned obj) {

        boolean diapasonX = (this.getPosition2().getX() >= obj.getPosition().getX())
                && (obj.getPosition().getX() >= this.getPosition().getX());
        boolean diapasonY = (this.getPosition2().getY() >= obj.getPosition().getY())
                && (obj.getPosition().getY() >= this.getPosition().getY());
        boolean diapasonZ = (this.getPosition2().getY() >= obj.getPosition().getY())
                && (obj.getPosition().getY() >= this.getPosition().getY());

        return diapasonX && diapasonY && diapasonZ;
    }

    @Override
    public void run(int x, int y) {
        Point newPos = new Point(this.getPosition().getX() + x, this.getPosition().getY() + y,
                this.getPosition().getZ());
        this.setPosition(newPos);
    }

}
