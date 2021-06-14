package hw16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CalculatorImpl implements Calculator {
    @Cacheable/*(H2DB.class)*/
    public List<Integer> fibonachi(int n) {
        if (n < 2) {
            n = 2;
        }
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1));
        for (int i = 2; i <= n; i++) {
            list.add(list.get(i - 1) + list.get(i - 2));
        }
        return list;
    }
}
