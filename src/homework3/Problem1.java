package homework3;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by chenhaiyan on 2016/12/27.
 */
public class Problem1 {
    public static void main(String[] args) {
        Integer[] arr=new Integer[]{1,32,4};
        List<Integer> list=Arrays.asList(arr);
        Integer[] arr1=new Integer[list.size()];
        list.toArray(arr1);
        System.out.println(list);
        System.out.println(Arrays.toString(arr1));
    }
}
