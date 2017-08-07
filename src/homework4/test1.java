package homework4;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenhaiyan on 2016/12/30.
 */
public class test1 {
    public static void main(String[] args) {
        List<String> list= Arrays.asList("a1","a2","b1","b2","c1","c2");
        list.stream()
                .filter(s -> s.startsWith("a"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }
}
