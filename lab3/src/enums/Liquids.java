package src.enums;

public enum Liquids {
        WATER("вода"), 
        WINE("вино"),
        BEER("пиво"),
        KOMPOT("компот"),
        MILKSHAKE("милкшэйк"),
        MILK("молоко"),
        BRAGA("брага");

        private final String translation;

        Liquids(String translation) {
            this.translation = translation;
        }

        public String getTranslation(){
                return this.translation;
        }
        
}
