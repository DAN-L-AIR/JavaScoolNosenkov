package hw3;

import java.util.HashMap;
import java.util.Map;

public class generic1 {

    public static void main(String[] args) {
        System.out.println("generics 1");
        CountMap<Integer> myCounMap1 = new CountMapImpl<>();
        System.out.println("-------------CountMap.add()-----------");
        myCounMap1.add(1);
        myCounMap1.add(2);
        myCounMap1.add(2);
        myCounMap1.add(3);
        myCounMap1.add(3);
        myCounMap1.add(3);
        myCounMap1.add(4);
        System.out.println("-------------CountMap.getCount()-----------");
        System.out.println("myCounMap1.getCount(0) = " + myCounMap1.getCount(0));
        System.out.println("myCounMap1.getCount(1) = " + myCounMap1.getCount(1));
        System.out.println("myCounMap1.getCount(2) = " + myCounMap1.getCount(2));
        System.out.println("myCounMap1.getCount(3) = " + myCounMap1.getCount(3));
        System.out.println("myCounMap1.getCount(4) = " + myCounMap1.getCount(4));
        System.out.println("-------------CountMap.remove()-----------");
        System.out.println("myCounMap1.remove(4) = " +  myCounMap1.remove(4));
        System.out.println("myCounMap1.getCount(4) = " + myCounMap1.getCount(4));
        System.out.println("-------------CountMap.size()-----------");
        System.out.println("myCounMap1.size = " + myCounMap1.size());
        CountMap<Integer> myCounMap2 = new CountMapImpl<>();
        System.out.println("-------------CountMap.addAll()-----------");
        System.out.println("-------------CountMap2.add(3,3,4)-----------");
        myCounMap2.add(3);
        myCounMap2.add(3);
        myCounMap2.add(4);
        myCounMap2.add(4);
        System.out.println("-------------CountMap1.addAll(CountMap2)-----------");
        myCounMap1.addAll(myCounMap2);
        System.out.println("myCounMap1.getCount(0) = " + myCounMap1.getCount(0));
        System.out.println("myCounMap1.getCount(1) = " + myCounMap1.getCount(1));
        System.out.println("myCounMap1.getCount(2) = " + myCounMap1.getCount(2));
        System.out.println("myCounMap1.getCount(3) = " + myCounMap1.getCount(3));
        System.out.println("myCounMap1.getCount(4) = " + myCounMap1.getCount(4));
        System.out.println("-------------CountMap.toMap()-----------");
        Map<Integer, Integer> map = myCounMap1.toMap();
        for(Map.Entry<Integer, Integer> entry: map.entrySet()) {
            System.out.println("Key=" + entry.getKey() + " Value=" + entry.getValue());
        }
        System.out.println("-------------CountMap.toMap(Map destination)-----------");
        Map<Integer, Integer> map1 = new HashMap<>();
        myCounMap1.toMap(map1);
        for(Map.Entry<Integer, Integer> entry: map1.entrySet()) {
            System.out.println("Key=" + entry.getKey() + " Value=" + entry.getValue());
        }
    }
}
