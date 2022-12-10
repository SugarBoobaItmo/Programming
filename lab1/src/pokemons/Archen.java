package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Archen extends Pokemon {
    public Archen(String name, int lvl) {
        super(name, lvl);

        this.setStats(55, 112, 45, 74, 45, 70);
        this.setType(Type.ROCK, Type.FLYING);
        this.setMove(new DoubleTeam(), new Confide(), new Leer());
    }

}
