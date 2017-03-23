package homework4;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by chenhaiyan on 2016/12/30.
 */
public class Test2 {
    public static void main(String[] args) {
        Stream.of("a1","a2","a3")
                .findFirst()
                .ifPresent(System.out::println);
        IntStream.range(1,4)
                .forEach(System.out::println);
        Arrays.stream(new int[]{1,2,3})
                .map(n->2*n+1)
                .forEach(System.out::println);
        Stream.of("a1","a2","a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);
    }
}
