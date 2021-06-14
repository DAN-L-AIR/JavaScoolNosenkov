package hw16;

public class JDBC_prog {
    public static void main(String[] args) {
        System.out.println("Программа по д/з JDBC");

       // Calculator calculator = (Calculator)new CacheProxy("~/db"/*System.getProperty("user.dir")*/).cache(new CalculatorImpl());
        Calculator calculator = (Calculator)new CacheProxy("/" + System.getProperty("user.dir") + "/DB").cache(new CalculatorImpl());
        // кеш (DB) пустой. Вычислим и положим в кеш (DB) аргументы 2,4,6,8,10 и результаты для них
        System.out.println("------Чётные------");
        for(int i = 2; i < 10; i+=2) {
            System.out.print("F(" + i + ")=");
            System.out.println(calculator.fibonachi(i));
        }
        System.out.println("------Чётные и Нечётные------");
        // кеш (DB)содержит данные для аргументов:2,4,6,8,10)
        for(int i = 2; i < 10; i++) {
            System.out.print("F(" + i + ")=");
            System.out.println(calculator.fibonachi(i));
        }
        System.out.println("------------");
    }
}
