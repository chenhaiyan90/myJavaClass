package homework7;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by chenhaiyan on 2017/2/7.
 */
public class Problem1 {
    public static void main(String[] args) {
        SynchronousQueue<String> queue=new SynchronousQueue();
        if(queue.offer("S1"))
        {
            System.out.println("scucess");
        }else
        {
            System.out.println("failed");
        }
    }
}
