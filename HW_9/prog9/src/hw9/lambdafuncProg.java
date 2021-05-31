package hw9;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class lambdafuncProg {

    public static void main(String[] args) {
        System.out.println("Д/з по теме лямбда и стрим-апи");
        List<Person> p = Arrays.asList(
                new Person("Nick", 20),
                new Person("Bob", 18),
                new Person("Ronald", 31),
                new Person("Peter", 44)
        );
        Map<String, Person> resMap = Streams.of(p)
                .filter((o) -> o.getAge() > 21)
                .transform((o) -> new Person(o.getName(),o.getAge() + 30))
                .toMap((o) -> o.getName(), (o) -> o);
        resMap.forEach((o, o1) -> System.out.println(o1));
        System.out.println("---Исходная коллекция неизменна-----");
        p.forEach(o-> System.out.println(o));
    }
}
