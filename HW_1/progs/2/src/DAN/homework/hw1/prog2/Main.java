package DAN.homework.hw1.prog2;

import java.util.Scanner;

/*
2035. Период массива

Периодом массива a=(a1, a2,..., an) называется такой его кратчайший подмассив b=(a1, a2,..., ak), что k делит n,
а будучи записанным n/k раз подряд он в точности окажется равным a.

Например, у массива (1, 2, 1, 1, 2, 1, 1, 2, 1) длина периода равна 3, а сам период — (1, 2, 1), у массива (1, 1, 1)
длина периода равна 1, а у массива (1, 2, 3, 4) — длина периода равна 4.

Ваша задача по заданному массиву найти длину его периода.

Входные данные
В первой строке записано целое положительное число n (1 ≤ n ≤ 10000), где n — длина заданного массива.
Вторая строка содержит последовательность целых
чисел a1, a2,..., an (1 ≤ ai ≤ 10000) — последовательность элементов заданного массива.

Выходные данные
Выведите искомую длину периода.


Пример(ы)
input.txt
output.txt
9
1 2 1 1 2 1 1 2 1
3

input.txt
output.txt
3
1 1 1
1

input.txt
output.txt
4
1 2 3 4
4
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int arrayLength = scanner.nextInt();
        int array[] = new int[arrayLength];

        for (int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
        }

        int arrayPeriodLength;

        for (arrayPeriodLength = 1; arrayPeriodLength <= array.length; arrayPeriodLength++) {
            if (arrayLength % arrayPeriodLength != 0) {
                continue;
            }
            int repeatTestNum = arrayLength / arrayPeriodLength;
            boolean isPeriod = true;
            for (int i = 1; i < repeatTestNum; i++) {
                for (int j = 0; j < arrayPeriodLength; j++) {
                    if (array[j] != array[i * arrayPeriodLength + j]) {
                        isPeriod = false;
                        // можно оптимизировать
                    }
                }
            }
            if (isPeriod) {
                break;
            }
        }

        System.out.println(arrayPeriodLength);
    }
}
