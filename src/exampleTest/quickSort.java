package exampleTest;

import java.util.Arrays;

/**
 * Created by chenhaiyan on 2016/12/22.
 */
public class quickSort {

    public static void main(String[] args) {
        int[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3 };
        System.out.print("Before sort:");
        System.out.println(Arrays.toString(array));
        quickSort(array);
        System.out.print("After sort:");
        System.out.println(Arrays.toString(array));
    }
    public static void quickSort(int[] array) {
        subQuickSort(array, 0, array.length - 1);
    }
    private static void subQuickSort(int[] array, int start, int end) {
        if (array == null || (end - start + 1) < 2) {
            return;
        }
        int part = partition(array, start, end);
        if (part == start) {
            subQuickSort(array, part + 1, end);
        } else if (part == end) {
            subQuickSort(array, start, part - 1);
        } else {
            subQuickSort(array, start, part - 1);
            subQuickSort(array, part + 1, end);
        }
    }
    private static int partition(int[] array, int start, int end) {
        int value = array[end];
        int index = start - 1;
        for (int i = start; i < end; i++) {
            if (array[i] < value) {
                index++;
                if (index != i) {
                    array[index]=array[index]^array[i];
                    array[i]=array[index]^array[i];
                    array[i]=array[index]^array[i];
                }
            }
        }
        if ((index + 1) != end) {
            array[index+1]=array[index+1]^array[end];
            array[end]=array[index+1]^array[end];
            array[index+1]=array[index+1]^array[end];
        }
        System.out.print(Arrays.toString(array));
        System.out.print(start);
        System.out.print(end);
        System.out.println(index+1);
        return index + 1;
    }
}
