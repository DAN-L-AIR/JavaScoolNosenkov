package hw2;

import java.util.*;

//Задание 1: Подсчитайте количество различных слов в файле.
// и
//Задание 2: Выведите на экран список различных слов файла, отсортированный по возрастанию их
// длины (компаратор сначала по длине слова, потом по тексту).

class StringByLenCompare implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int result = 0;
        if (o1.length() < o2.length()) {
            result = -1;
        } else if (o1.length() > o2.length()) {
            result = 1;
        } else {
            result = o1.compareTo(o2);
        }
        return result;
    }
}


//Задание 5: Реализуйте свой Iterator для обхода списка в обратном порядке
class DecrementIterator<T> implements Iterator<T> {
    List<T> lr;
    private Integer index = -1;

    public DecrementIterator(List<T> l) {
        lr = l;
        if (lr == null) {
            index = null;
        } else {
            index = lr.size() - 1;
        }
    }

    @Override
    public boolean hasNext() {
        return index != null && index != -1;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return lr.get(index--);
        } else {
            return null;
        }
    }
}

class ListWithDecrementIterator<T> extends ArrayList<T> {

    private DecrementIterator<T> dI;
    public DecrementIterator<T> DecrementIterator(){
         dI = new <T>DecrementIterator(this);
        return dI;
    }
}

public class Main {
    public static void main(String[] args) {
        String[] words = {"индейка", "батут", "капот", "из", "после", "индейка", "из", "из"};

        StringByLenCompare myComparator = new StringByLenCompare();
        SortedSet<String> uniqWords = new TreeSet<>(myComparator);
        int count = 0;
        System.out.println("\n----------Исходный 'файл'------------");
        for (String word : words) {
            System.out.print("<" + word + "> ");
            if (uniqWords.add(word)) {
                count++;
            }
        }
        System.out.println("\n----------Кол-во различных слов------------");
        System.out.println("Всего слов:" + words.length + " из них уникальных " + uniqWords.size());
        System.out.println("\n----------Сортировка по длине строки и слову------------");
        Iterator<String> it = uniqWords.iterator();
        while (it.hasNext()) {
            System.out.print("<" + it.next() + "> ");
        }
//Задание 3: Подсчитайте и выведите на экран сколько раз каждое слово встречается в файле.
        System.out.println("\n----------Сколько раз каждое слово встречается в файле------------");
        Map<String, Integer> uniqWordsMap = new HashMap();
        for (String word : words) {
            Integer count1 = uniqWordsMap.get(word);
            if (count1 == null) {
                uniqWordsMap.put(word, 1);
                //System.out.println(" new: <" + word + ">" );
            } else {
                uniqWordsMap.put(word, ++count1);
                //System.out.println("Cчётчик для <" + word + "> увеличен и стал равен " + count1);
            }
        }

        it = uniqWordsMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("Слово <" + key + "> найдено " + uniqWordsMap.get(key) + " раз");
        }
        System.out.println("\n----------прямой порядок--------------");
//Задание 4: Выведите на экран все строки файла в обратном порядке.
        List<String> wordsList = new ArrayList<>();
        for (String word : words) {
            wordsList.add(word);
        }
        it = wordsList.iterator();
        while (it.hasNext()) {
            System.out.print("<" + it.next() + " >");
        }
        Collections.reverse((List<String>) wordsList);
        System.out.println("\n----------обратный порядок--------------");
        it = wordsList.iterator();
        while (it.hasNext()) {
            System.out.print("<" + it.next() + " >");
        }

        System.out.println("\n----------с помощью реализации свонго 'обратного' итератора --------------");
//Задание 5: Реализуйте свой Iterator для обхода списка в обратном порядке.
        ListWithDecrementIterator<String> wordMyList = new ListWithDecrementIterator<>();
        for (String word : words) {
            wordMyList.add(word);
        }

        DecrementIterator<String> dIt = wordMyList.DecrementIterator();
        while (dIt.hasNext()) {
            System.out.print("<" + dIt.next() + " >");
        }
        //Задание 6: Выведите на экран строки, номера которых задаются пользователем в произвольном порядке.
        Collections.reverse((List<String>) wordsList);
        System.out.println("\n----------произвольные элементы списка --------------");
        System.out.println("Пользователь задал №0 " + wordsList.get(0));
        System.out.println("Пользователь задал №7 " + wordsList.get(7));
        System.out.println("Пользователь задал №3 " + wordsList.get(3));
        System.out.println("Пользователь задал №2 " + wordsList.get(2));
    }
}
