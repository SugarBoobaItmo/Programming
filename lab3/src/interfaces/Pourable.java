package src.interfaces;
import src.abs.Thing;
import src.enums.Liquids;

public interface Pourable {
    public String pour(Liquids liquid, Thing thing);
}
