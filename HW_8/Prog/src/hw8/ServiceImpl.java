package hw8;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service{
    @Override
    public List<String> run(String item, double value, Date date) {
        List<String> list = new ArrayList<>();
        while(value > 0){
            list.add(new StringBuilder().append(item).append(value).append(date).toString());
            value -= 1.0;
        }
        return list;
    }

    @Override
    public List<String> work(String item) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < (item.length() - 1); i++){
            list.add(item.substring(0, i + 1));
        }
        return list;
    }
}
