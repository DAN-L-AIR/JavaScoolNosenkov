package hw8;

import java.util.Date;
import java.util.Random;

public class SerialisationProg {
    public static void main(String[] args) {
        System.out.println("Программа по д/з 'Cериализация'");
	    CacheProxy cacheProxy = new CacheProxy(System.getProperty("user.dir"));
        Service service = (Service) cacheProxy.cache(new ServiceImpl());

        System.out.println("empty cache");
        long ts = System.nanoTime();
        service.run("str ", 111.0, new Date());
        System.out.println(System.nanoTime() - ts);

//        ts = System.nanoTime();
//        service.work("Date() Этот конструктор инициализирует объект с текущей датой и временем.");
//        System.out.println(System.nanoTime() - ts);

        System.out.println("full cache");
        ts = System.nanoTime();
        service.run("str ", 111.0, new Date());
        System.out.println(System.nanoTime() - ts);

//        ts = System.nanoTime();
//        service.work("Date() Этот конструктор инициализирует объект с текущей датой и временем.");
//        System.out.println(System.nanoTime() - ts);
    }
}
