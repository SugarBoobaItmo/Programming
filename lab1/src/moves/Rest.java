package moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected String describe() {
        return "спит 2 оборота, чтобы полностью восстановиться";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect.sleep(p);
        
        double fullHP = p.getStat(Stat.HP);
        double hpOffset = p.getHP() - fullHP;
        p.setMod(Stat.HP, (int) hpOffset);
    }
}
