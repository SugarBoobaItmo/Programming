import java.io.Serializable;

public class Person implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private boolean sex;

    public Person(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public boolean getSex(){
        return sex;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }


}
