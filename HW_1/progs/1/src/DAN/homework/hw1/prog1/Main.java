package DAN.homework.hw1.prog1;

import java.util.Scanner;

/*
2013	  Ветвления, циклы. Количество минимумов
Задана последовательность n целых чисел a1, a2,..., an. Выведите количество ее элементов, равных минимальному.

Входные данные
В первой строке записано целое число n (1 ≤ n ≤ 10000). Вторая строка содержит последовательность n целых чисел
 a1, a2,..., an (-10000 ≤ ai ≤ 10000).

Выходные данные
Выведите искомое количество минимумов.

Пример(ы)
input.txt
output.txt
5
4 2 3 2 14
2
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int count = scanner.nextInt();
        int min = 10_000;
        int minCount = 0;
        for (int i = 0; i < count; i++) {
            int curentInt = scanner.nextInt();
            if (curentInt < min) {
                min = curentInt;
                minCount = 1;
            } else if (curentInt == min) {
                minCount++;
            }
        }
        System.out.println(minCount);
    }
}
