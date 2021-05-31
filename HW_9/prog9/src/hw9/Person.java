package hw9;

public class Person {
    private Integer age;
    private String name;

    public Person(String name, Integer age) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        //String result = "Person<Name:" + getName() + " : Age:" + getAge() + ">";
        return "Person<Name:" + getName() + " : Age:" + getAge() + ">";
    }
}
