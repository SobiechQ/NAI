package Utils;

import java.util.Comparator;
import java.util.List;

public class Utils {
    public static <T> void sort(List<T> list, Comparator<T> comparator){
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
    public static <T> void shuffle(List<T> list){
        for (int i = 0; i < list.size(); i++) {
            int index = (int) (Math.random() * list.size());
            T temp = list.get(i);
            list.set(i, list.get(index));
            list.set(index, temp);
        }
    }

}
