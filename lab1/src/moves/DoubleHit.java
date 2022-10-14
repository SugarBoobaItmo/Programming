package moves;

import ru.ifmo.se.pokemon.*;

public class DoubleHit extends PhysicalMove {
    public DoubleHit() {
        super(Type.NORMAL, 35, 90);
        this.hits = 2;
    }

    @Override
    protected String describe() {
        return "бьет врага хвостом";
    }
}
