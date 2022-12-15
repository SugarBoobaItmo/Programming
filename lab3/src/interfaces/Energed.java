package src.interfaces;

import src.exceptions.IncorrectSizeException;

public interface Energed {
    public void relax(int duration);

    public void work(int duration) throws IncorrectSizeException;

    public boolean isTired();
}
