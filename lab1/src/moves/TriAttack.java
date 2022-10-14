package moves;

import ru.ifmo.se.pokemon.*;

public class TriAttack extends SpecialMove {
    public TriAttack() {
        super(Type.NORMAL, 80, 100);

    }

    @Override
    protected String describe() {
        return "стреляет тремя видами лучей одновременно";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect();

        if (e.chance(0.0667).success()) {
            Effect.paralyze(p);
            System.out.println("PARALIZE");
        }
        if (e.chance(0.0667).success()) {
            Effect.burn(p);
            System.out.println("BURN");
        }
        if (e.chance(0.0667).success()) {
            Effect.freeze(p);
            System.out.println("FREEZE");
        }

    }

}
