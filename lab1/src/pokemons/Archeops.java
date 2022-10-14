package pokemons;
import ru.ifmo.se.pokemon.*;
import moves.*;

public class Archeops extends Archen{
    public Archeops(String name, int lvl){
        super(name, lvl);

        this.setStats(75, 140, 65, 112, 65, 110);
        this.addMove(new FocusBlast());
    }    
}
