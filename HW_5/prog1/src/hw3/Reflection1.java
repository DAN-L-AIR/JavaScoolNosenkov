package hw3;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class CalculatorImpl implements Calculator {
    public static final int notString = 1;
    private static final String SUNDAY = "SUN_DAY";
    public final String JEUDI = "JE_UDI";
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNSDAY = "WEDNSDAY";
    float Z;
    private int x;
    private String y;
    private short a;

    public short getA() {
        return a;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public int calc(int number) {
        if (number == 1) {
            return 1;
        } else {
            return number * calc(number - 1);
        }
    }

    private void CalcPrivate() {
    }
}

public class Reflection1 {

    public static void main(String[] args) {
        System.out.println("------------1. Имплементировать интерфейс Calculator в классе CalculatorImpl---------\n");
        Calculator calculator = new CalculatorImpl();
        int number = 5;
        System.out.println("Факториал(" + number + ") = " + calculator.calc(number));
        System.out.println("\n------------2. Вывести все методы класса, включая методы родительских классов, в т.ч. приватные \n");
        Class<?> cl = CalculatorImpl.class;
        while (cl != null) {
            //System.out.println("В классе " + cl + " объявелены методы:");
            Method[] mm = cl.getDeclaredMethods();
            for (Method cm : mm) {
                System.out.println("\t" + cm);
            }
            //System.out.println("-----------" + cl + "-------------------");
            cl = cl.getSuperclass();
        }
        System.out.println("\n------------3. Вывести все геттеры класса \n");
        Field[] cf = CalculatorImpl.class.getDeclaredFields();
        for (Field f : cf) {
            //Class fc = f.getType();
            System.out.print(f.getName());
            String getterName = "get" + f.getName().toUpperCase();
            Method getter = null;
            try {
                getter = CalculatorImpl.class.getDeclaredMethod(getterName);
            } catch (NoSuchMethodException e) {
                System.out.println(" - getter not found");
                continue;
            }
            System.out.println(" - " + getter);
        }

        System.out.println("\n------------4. Проверить что все String константы имеют значение = их имени \n");
        boolean allFinalStringsHasValueEqualName = true;
        boolean stringConstantFound = false;
        for (Field f : cf) {
            int mod = f.getModifiers();
            String x = f.getType().getName();
            if ((!f.getType().getName().contains("String") ||
                    !Modifier.isFinal(mod)) ||
                    !Modifier.isPublic(mod) ||
                    !Modifier.isStatic(mod)) {
                continue;
            }
            stringConstantFound = true;
            String Value = null;
            try {
                Value = (String) f.get(calculator);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!Value.equals(f.getName())) {
                System.out.println("Field " + f.getName() + " not equal value " + Value);
                allFinalStringsHasValueEqualName = false;
                break;
            }
        }
        if(stringConstantFound) {
            System.out.print(allFinalStringsHasValueEqualName ? "All " : "Not all ");
            System.out.println("string constants have value equal to name");
        }
        else{
            System.out.println("String constants not found");
        }
    }
}
