package moves;

import ru.ifmo.se.pokemon.*;

public class XScissor extends PhysicalMove {
    public XScissor() {
        super(Type.BUG, 80, 100);

    }

    @Override
    protected String describe() {
        return "наносит удар по врагу, скрещивая косы, как если бы они были парой ножниц.";
    }

}