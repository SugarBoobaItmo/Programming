package src.classes;

import src.abs.Creature;
import src.enums.Properties;
import src.interfaces.Runnable;
import src.interfaces.Snortable;

public class Rabbit extends Creature implements Runnable, Snortable {
    public Rabbit(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "Теперь я знаю, где мы!";
    }


    @Override
    public void relax(int duration) {
        this.setEnergy(this.getEnergy()+duration+10);
    }

    @Override
    public void work(int duration) {
        if ((this.getEnergy() - duration) >= 3) {
            this.setEnergy(this.getEnergy()-duration);
        } else {
            this.setEnergy(0);
        }
        if (this.getCreatureSize() > 1) {
            this.setCreatureSize(this.getCreatureSize()-1);
        }
    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 3; 
    }

    @Override
    public void run(int x, int y) {
        Point newPos = new Point(this.getPosition().getX() + x, this.getPosition().getY() + y, this.getPosition().getZ());
        this.setPosition(newPos);
    }

    @Override 
    public void snort(){
        if (this.getEnergy()-1>0){
            this.setEnergy(getEnergy()-1);
        }
    }
    

    
}
