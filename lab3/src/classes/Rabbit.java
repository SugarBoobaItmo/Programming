package src.classes;

import src.abs.Animal;
import src.enums.Properties;
import src.interfaces.Snortable;

public class Rabbit extends Animal implements Snortable {
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
        try {
            if (this.getCreatureSize() > 1) {
                this.setCreatureSize(this.getCreatureSize()-1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible state");
        }
    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 3; 
    }

    @Override 
    public void snort(){
        if (this.getEnergy()-1>0){
            this.setEnergy(getEnergy()-1);
        }
    }
    
    @Override 
    public void eat(){
        this.setEnergy(getEnergy()+5);
        try {
            
            this.setCreatureSize(getCreatureSize()+1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(this.getName()+"немного переел");
    }
    }

    
}
