package hw3;

import static hw3.BeanUtils.assign;

class Source {
    String aBc;
    Integer DEf;

    public void getNoRet(){

    }

    public String getaBc() {
        return aBc;
    }

    public void setaBc(String aBc) {
        this.aBc = aBc;
    }

    public Integer getDEf() {
        return DEf;
    }

    public void setDEf(Integer DEf) {
        this.DEf = DEf;
    }

    @Override
    public String toString() {
        return "<" + aBc + ":" + DEf + ">";
    }
}

class Dest {
    String aBc;
    Number DEf;

    public String getaBc() {
        return aBc;
    }

    public void setaBc(String aBc) {
        this.aBc = aBc;
    }

    public Number getDEf() {
        return DEf;
    }

    public void setDEf(Number DEf) {
        this.DEf = DEf;
    }

    @Override
    public String toString() {
        return "<" + aBc + ":" + DEf + ">";
    }
}

public class prog7 {

    public static void main(String[] args) {
        System.out.println("\n------------7. Создать класс BeanUtils (см. документацию в файле класса) ");
        Source src = new Source();
        Dest dst = new Dest();
        Source dst2 = new Source();
        System.out.println("src= " + src);
        System.out.println("dst= " + dst);
        System.out.println("dst2= " + dst2);
        src.setaBc("1111str");
        src.setDEf(1);
        System.out.println("src= " + src);
        assign(dst, src);
        assign(dst2, src);
        System.out.println("dst= " + dst);
        System.out.println("dst2= " + dst2);
    }
}
