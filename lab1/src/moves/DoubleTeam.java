package moves;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected String describe() {
        return "усиливает уклончивость";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e = new Effect().turns(-1).stat(Stat.EVASION, 1);
        p.addEffect(e);
    }

}
