package moves;

import ru.ifmo.se.pokemon.*;

public class FocusBlast extends SpecialMove {
    public FocusBlast() {
        super(Type.FIGHTING, 120, 70);

    }

    @Override
    protected String describe() {
        return "усиливает ментальную сосредоточенность и высвобождает силу";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().chance(0.1).turns(-1).stat(Stat.SPECIAL_DEFENSE, -1);
        p.addEffect(e);
    }

}
