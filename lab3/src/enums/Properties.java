package src.enums;

public enum Properties {
    SMALL("Маленькая"),
    SAD("Грустный"),
    SCARED("Испуганный"),
    CLEVER("Умный"),
    STUPID("Глупый"),
    FRIENDLY("Дружественный"),
    OBEDIENT("Послушный"),
    KIND("Добрый"),
    BIG("Большой"),
    SAVING("Спасительный"),
    BESTOFALL("Лучший из лучших"),
    STRICT("Строгая");

    public final String translation;

    Properties(String translation) {
        this.translation = translation;
    }

    
    public String getTranslation() {
        return "Перевод на русский " + translation;
    }

}