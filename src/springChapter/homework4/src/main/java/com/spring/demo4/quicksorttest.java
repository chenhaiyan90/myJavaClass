package com.spring.demo4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhaiyan on 2017/12/4.
 */
public class quicksorttest {
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            strs.add(String.valueOf(string.charAt(getRandom(string.length()-1))));
        }
        List<String> ss=quickSort(strs);
        strs.stream().forEach(a->{System.out.print(a);});
        ss.stream().forEach(a->{System.out.print(a);});
    }

    private static String string = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }

    public static List<String> quickSort(List<String> ss){
        if(ss.size()<=1){
            return ss;
        }
        int middleFlag = ss.size() / 2;
        String middleElement = ss.get(middleFlag);
        List<String> firstStrs=new ArrayList<>();
        List<String> secondStrs=new ArrayList<>();
        for (String a:ss
             ) {
            if(middleElement.compareTo(a)>0){
                firstStrs.add(a);
            }else {
                secondStrs.add(a);
            }
        }
        List<String> result = null;
        List<String> result2 = null;

        if(firstStrs.size()>0){
            result=quickSort(firstStrs);
        }
        if (secondStrs.size()>0){
            result2=quickSort(secondStrs);
        }
        result.addAll(result2);
        return result;

    }
}
