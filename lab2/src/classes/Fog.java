package src.classes;

import src.abs.Location;
import src.abs.Positioned;

public class Fog extends Location {
    public Fog(Point pos, String name, Point pos2) {
        super(pos, name, pos2);
    }

    public String describe() {
        return "Локация " + this.getName();
    }

    public boolean checkConsist(Positioned obj) {
        boolean diapasonX = (this.getPosition2().x >= obj.getPosition().x)
                && (obj.getPosition().x >= this.getPosition().x);

        boolean diapasonY = (this.getPosition2().y >= obj.getPosition().y)
                && (obj.getPosition().y >= this.getPosition().y);

        return diapasonX && diapasonY;
    }

}
