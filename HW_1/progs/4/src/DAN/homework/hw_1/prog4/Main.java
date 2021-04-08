package DAN.homework.hw_1.prog4;

public class Main {

    public static void main(String[] args) {
        Person [] p = new Person[4];
        p[0] = new Person(true, "Ken");
        p[1] = new Person(false, "Pepa");
        p[2] = new Person(true, "Tom");
        p[3] = new Person(false, "Barbie");
        for (Person p_: p) {
            System.out.println(p_);
        }
        System.out.println("-----------------------");
        p[0].marry(p[1]);
        p[2].marry(p[3]);
        for (Person p_: p) {
            System.out.println(p_);
        }
        System.out.println("-----------------------");
        p[0].marry(p[3]);
        for (Person p_: p) {
            System.out.println(p_);
        }
        p[2].marry(p[1]);
        System.out.println("-----------------------");
        p[0].marry(p[3]);
        for (Person p_: p) {
            System.out.println(p_);
        }
    }
}
