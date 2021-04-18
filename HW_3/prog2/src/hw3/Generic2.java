package hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Generic2 {

    public static void main(String[] args) {
        System.out.println("Generic2");
        System.out.println("----------CollectionUtils.add()------------");
        List<Integer> list1 = new ArrayList<>();
        CollectionUtils.add(list1, 1);
        CollectionUtils.add(list1, -1);
        System.out.println(list1);
        System.out.println("----------CollectionUtils.addAll()------------");
        List<Integer> list2 = new ArrayList<>();
        CollectionUtils.add(list2, 0);
        CollectionUtils.add(list2, 7);
        CollectionUtils.addAll(list1, list2);
        System.out.println(list2);
        System.out.println("----------CollectionUtils.containsAll()------------");
        System.out.println("CollectionUtils.containsAll(list2, list1):" + CollectionUtils.containsAll(list2, list1));
        System.out.println("CollectionUtils.containsAll(list1, list2):" + CollectionUtils.containsAll(list1, list2));
        System.out.println("----------CollectionUtils.containsAny()------------");
        List<Integer> list3 = new ArrayList<>();
        CollectionUtils.add(list3, 0);
        System.out.println("CollectionUtils.containsAny(list2, list3):" + CollectionUtils.containsAny(list2, list3));
        System.out.println("CollectionUtils.containsAny(list1, list3):" + CollectionUtils.containsAny(list1, list3));
        System.out.println("----------CollectionUtils.Limit()------------");
        System.out.println("CollectionUtils.limit(list2, 2) =" + CollectionUtils.limit(list2, 2));
        System.out.println("----------CollectionUtils.newArrayList()------------");
        List<Integer> list4 = CollectionUtils.newArrayList();
        list4.add(1971);
        list4.add(2004);
        list4.add(2017);
        System.out.println(list4);
        System.out.println("----------CollectionUtils.range()------------");
        System.out.println("CollectionUtils.range(Arrays.asList(8, 1, 3, 5, 6, 4), 3, 6)) = " + CollectionUtils.range(Arrays.asList(8, 1, 3, 5, 6, 4), 3, 6));
        System.out.println("----------CollectionUtils.range( ,comparator)------------");

        List<? super Integer> list6 = CollectionUtils.range(Arrays.asList(8, 1, 3, 5, 6, 4), 3, 6, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 < o2) {
                    return -1;
                } else if (o1 == o2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        System.out.println(list6);
        System.out.println("----------CollectionUtils.ramoveAll()------------");
        System.out.println("list4 before:"+list4);
        CollectionUtils.removeAll(list4,Arrays.asList(2017,1971));
        System.out.println("list4 after remove:"+list4);
        System.out.println("----------CollectionUtils.indexOf()------------");
        list1.add(17);
        System.out.println("CollectionUtils.indexof()= "+CollectionUtils.indexOf(list1,17));
        }
    }
