package server;

public class Cat {
    private String name;
    private int age;
    private String color;
    private String additionalInfo;


    public Cat(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public void setAdditionalInfo(String string) {
        this.additionalInfo = string;
    }

    @Override
    public String toString() {
        return "Cat [name=" + name + ", age=" + age + ", color=" + color + ", additionalInfo=" + additionalInfo + "]";
    }
}
