package homework4;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by chenhaiyan on 2017/1/4.
 */
public class Test5 {
    public static void main(String[] args) {
        Arrays.asList("a1","a2","bw","cc").stream().forEach(System.out::println);//通过集合的stream()方法或者parallelStream()
        IntStream.range(1,100).limit(10).forEach(System.out::println);//随机数流
        Stream.iterate(0, n -> n * 2).limit(5).forEach(System.out::println);//使用流的静态方法
        Arrays.asList("ww","qqq","eee").stream().skip(1).forEach(System.out::println);//skip(i)返回跳过前i个元素的stream
        Stream.of(1,2,3,4,5).allMatch(o->o>3);//判断stream中元素是否匹配

    }
}
