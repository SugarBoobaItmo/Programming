package src.enums;

public enum Properties {
    SMALL("Маленькая"),
    SAD("Грустный"),
    SCARED("Испуганный"),
    CLEVER("Умный"),
    STUPID("Глупый");

    public final String translation;

    Properties(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "Перевод на русский " + translation;
    }

}