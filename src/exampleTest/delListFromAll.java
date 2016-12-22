package exampleTest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenhaiyan on 2016/12/21.
 */
public class delListFromAll {
    public static void main(String[] args) {

    }
    public static List removeAll(List src,List oth){
        LinkedList result=new LinkedList(src);
        HashSet othHash=new HashSet(oth);
        Iterator iter=result.iterator();
        while (iter.hasNext()){
            if(othHash.contains(iter.hasNext())){
                iter.remove();
            }
        }
        return result;
    }
}
