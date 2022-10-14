package moves;

import ru.ifmo.se.pokemon.*;

public class Leer extends StatusMove {
    public Leer() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected String describe() {
        return "уменьшает ЗАЩИТУ противника";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().turns(-1).stat(Stat.DEFENSE, -1);
        p.addEffect(e);
    }
}
