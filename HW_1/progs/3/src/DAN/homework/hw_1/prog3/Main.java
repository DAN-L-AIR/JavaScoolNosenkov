package DAN.homework.hw_1.prog3;
/*
2036. Строки. Странные слова

Дан набор из n слов, состоящих из маленьких латинских букв.
Будем называть слово странным, если в нем встречаются 3 или более гласные буквы подряд.
Ваша задача — удалить из данного набора все странные слова.
Гласными буквами в латинском алфавите считаются e,y,u,i,o,a.

Входные данные
В первой строке содержится число n — количество слов в наборе, n не превосходит 100. Далее в n строках по одному на строке содержатся слова из набора. Слова состоят только из маленьких латинских букв. Длина каждого слова не менее 1 и не более 20 символов.

Выходные данные
Выведите все слова из набора, не являющиеся странными. Каждое слово выводите на отдельной строке в том порядке, в котором они заданы во входных данных.
 */

import java.util.Scanner;

public class Main {

    private static boolean wordContainSeqVowels(char[] wordAsChar) {
        final char[] vowel = {'e', 'y', 'u', 'i', 'o', 'a'};
        int succVowelsCount = 0;
        for (int j = 0; j < wordAsChar.length; j++) {
            boolean vowelFound = false;
            for (int k = 0; k < vowel.length; k++) {
                if (wordAsChar[j] == vowel[k]) {
                    vowelFound = true;
                    break;
                }
            }
            succVowelsCount = vowelFound ? succVowelsCount + 1 : 0;
            if (succVowelsCount == 3) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int wordsNum = scanner.nextInt();
        String[] word = new String[wordsNum];

        for (int i = 0; i < wordsNum; i++) {
            word[i] = scanner.next();
        }

        for (int i = 0; i < word.length; i++) {
            if (!wordContainSeqVowels(word[i].toCharArray())) {
                System.out.println(word[i]);
            }
        }
    }
}
