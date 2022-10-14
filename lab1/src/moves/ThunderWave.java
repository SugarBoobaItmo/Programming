package moves;

import ru.ifmo.se.pokemon.*;

public class ThunderWave extends StatusMove {
    public ThunderWave() {
        super(Type.ELECTRIC, 0, 90);

    }

    @Override
    protected String describe() {
        return "делает движение, которое может вызвать паралич";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.paralyze(p);
    }

}
