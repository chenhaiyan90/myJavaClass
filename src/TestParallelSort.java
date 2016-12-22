import java.util.Arrays;
import java.util.Random;
import java.util.function.IntUnaryOperator;

/**
 * Created by chenhaiyan on 2016/12/6.
 */
public class TestParallelSort {
    public static void main(String[] args) {
        int[] a=new int[1<<25];
        Arrays.setAll(a,e -> new Random().nextInt(100));
        int [] b=Arrays.copyOf(a,a.length);
        Long time1=System.currentTimeMillis();
        Arrays.parallelSort(a);
        Long time2=System.currentTimeMillis();
        Arrays.sort(b);
        Long time3=System.currentTimeMillis();
        System.out.println(time3-time2);
        System.out.println(time2-time1);
    }
}
