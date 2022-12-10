package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;

public class Keldeo extends Pokemon {
    public Keldeo(String name, int lvl) {
        super(name, lvl);

        this.setStats(90, 72, 90, 129, 90, 108);
        this.setType(Type.WATER, Type.FIGHTING);
        this.setMove(new Swagger(), new XScissor(), new FocusBlast(), new CloseCombat());

    }
}
