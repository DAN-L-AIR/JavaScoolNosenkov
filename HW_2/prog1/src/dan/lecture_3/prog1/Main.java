package dan.lecture_3.prog1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<car> park = new ArrayList<>();

        park.add(new car("Лада", "седан"));
        park.add(new car("Лада", "хэтчбек"));
        park.add(new car("Бмв", "кроссовер"));
        park.add(new car("Форд", "хэтчбек"));
        park.add(new car("Пежо", "кроссовер"));
        park.add(new car("Тойота", "седан"));

        park.add(new car("Камаз", "родстер"));
        park.add(new car("Т72Б", "родстер"));

        List<List<car>> typesList = new ArrayList<>();
        for (car cc : park) {
            boolean key = false;
            for (List<car> cl : typesList) {
                if (cc.compareTo(cl.get(0)) == 0) {
                    cl.add(cc);
                    key = true;
                    break;
                }
            }
            if (!key) {
                List<car> nl = new ArrayList<>();
                nl.add(cc);
                typesList.add(nl);
            }
        }
        System.out.println("total list number = " + typesList.size());
        System.out.println("--------- lists by types---------");
        System.out.println(typesList);



        Collections.sort(park);
        Iterator<car> it = park.iterator();
        car pc = null;
        car cc = null;
        int firstIndex = 0;
        int lastIndex = 0;
        while (it.hasNext()) {
            if (pc == null) {
                pc = it.next();
                lastIndex = firstIndex;
            } else {
                cc = it.next();
                if (pc.compareTo(cc) == 0) {
                    lastIndex++;
                }
            }
        }

//        for (car cc : park) {
//            int index =
//            for (List<car> cl : typesList) {
//                if (cc.compareTo(cl.get(0)) == 0) {
//                    cl.add(cc);
//                    key = true;
//                    break;
//                }
//            }
//            if (!key) {
//                List<car> nl = new ArrayList<>();
//                nl.add(cc);
//                typesList.add(nl);
//            }
//        }
//
//        System.out.println("---------before sort---------");
//        System.out.println(park);
//        Collections.sort(park);
//        System.out.println("---------after sort-------------");
//        System.out.println(park);
    }
}
