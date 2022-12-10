package src.classes;

import src.abs.Human;
import src.enums.Properties;

public class KrisRobin extends Human {
    public KrisRobin(Point pos, String name, int creatureSize, int energy, Properties[] personality) {
        super(pos, name, creatureSize, energy, personality);
    }

    @Override
    public String say() {
        return "Мы кажется кого-то забыли";
    }

    @Override
    public void relax(int duration) {
        this.setEnergy(this.getEnergy() + duration + 5);
    }

    @Override
    public void work(int duration) {
        if ((this.getEnergy() - duration) >= 1) {
            this.setEnergy(this.getEnergy() - duration);
        } else {
            this.setEnergy(0);
        }

        try {
            if (this.getCreatureSize() > 10) {
                this.setCreatureSize(this.getCreatureSize() - 10);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible state");
        }
    }

    @Override
    public boolean isTired() {
        return this.getEnergy() < 1;
    }

    @Override
    public void eat() {
        this.setEnergy(getEnergy() + 20);
        try {

            this.setCreatureSize(getCreatureSize() + 20);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(this.getName() + "немного переел");
        }
    }

}
