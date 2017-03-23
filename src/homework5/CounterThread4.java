package homework5;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenhaiyan on 2017/1/9.
 */
public class CounterThread4 extends Thread {
    public MyCounter myCounter;
    public final CountDownLatch latch= new CountDownLatch(10);
    public CounterThread4(String name, MyCounter abstractMyCounter){
        super(name);
        this.myCounter=abstractMyCounter;

    }

    @Override
    public void run() {
        long number=100_0000;
        while (number>0){
            myCounter.incr(null,null,null,null);
            number--;
        }
        latch.countDown();
        System.out.println(Thread.currentThread().getName()+"finished");
    }
}
