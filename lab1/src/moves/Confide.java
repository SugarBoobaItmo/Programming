package moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected String describe() {
        return "сообщает секрет, и цель теряет способность концентрироваться";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().turns(-1).stat(Stat.SPECIAL_ATTACK, -1);
        p.addEffect(e);
    }
}
