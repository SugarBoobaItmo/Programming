import pokemons.*;

import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();

        Pokemon[] ally = { 
            new Keldeo("Bob", 70), 
            new Archen("Arsen", 1), 
            new Archeops("Dex", 64) 
        };
        Pokemon[] foe = { 
            new Deino("Litty", 21), 
            new Zweilous("Artur", 1), 
            new Hydreigon("Loli", 1) 
        };
        for (int i = 0; i < ally.length; i++) {
            b.addAlly(ally[i]);
            b.addFoe(foe[i]);

        }
        b.go();
    }

}
