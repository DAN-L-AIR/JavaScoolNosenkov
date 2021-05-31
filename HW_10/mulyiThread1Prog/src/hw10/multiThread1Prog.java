package hw10;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FactTask implements Runnable{
    private final int id;
    private final long factArg;
    FactTask(int id, int factArg) {
        this.id = id;
        this.factArg = factArg;
    }

    @Override
    public void run() {
        System.out.println("Thread id " + id + " started ");
        int facValue = 1;
        for (int k = 2; k <= factArg; k++) {
            facValue *= k;
        }
        System.out.println("Thread " + id + " Факториал (" + factArg + ") = " + facValue);
    }
}

public class multiThread1Prog {

    public static void main(String[] args) {
        if (args.length != 0) {
            Integer[] nums = {40, 6, 2, 8, 1, 4};
            try {
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(args[0])));
                for (Integer num : nums) {
                    dos.writeInt(num);
                }
                dos.close();
            } catch (FileNotFoundException e) {
                System.out.println("Запись аргументов факториалов в файл: Ошибка создания файла");
                return;
            } catch (IOException e) {
                System.out.println("Запись аргументов факториалов в файл: Ошибка записи в файл");
                return;
            }
            System.out.println("Запись аргументов факториалов в файл: Файл успешно записан");

            List<Thread> ftr = new ArrayList<>();
            int id = 0;
            try {
                DataInputStream dis = new DataInputStream(new FileInputStream(new File(args[0])));
                while (true) {
                    try {
                        System.out.println();
                        ftr.add(new Thread(new FactTask(++id, dis.readInt())));
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Чтение аргументов факториалов из файлов: Ошибка открытия файла");
                return;
            } catch (IOException e) {
                System.out.println("Чтение аргументов факториалов из файлов: Ошибка чтения из файла");
                return;
            }
            ftr.forEach((o)-> o.start());
        } else {
            System.out.println("Запустите программу с параметром - именем файла с целыми числами (аргументами фкнуции факторила)");
        }
    }
}
