package moves;

import ru.ifmo.se.pokemon.*;

public class CloseCombat extends PhysicalMove {
    public CloseCombat() {
        super(Type.FIGHTING, 120, 100);
    }

    @Override
    protected String describe() {
        return "сражается с врагом в ближнем бою, не защищаясь";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e = new Effect().turns(-1).stat(Stat.DEFENSE, -1).stat(Stat.SPECIAL_DEFENSE, -1);
        p.addEffect(e);
    }

}
