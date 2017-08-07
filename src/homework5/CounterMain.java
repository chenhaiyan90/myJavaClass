package homework5;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenhaiyan on 2017/1/9.
 */
public class CounterMain {


    public static void main(String[] args) throws InterruptedException {
        CounterThread1 thread1=new CounterThread1(Integer.toString(1),new MyCounter((long)0,(long)0,(long)0));
        CounterThread2 thread2=new CounterThread2(Integer.toString(2),new MyCounter((long)0,(long)0,(long)0));
        CounterThread3 thread3=new CounterThread3(Integer.toString(3),new MyCounter((long)0,(long)0,(long)0));
        CounterThread4 thread4=new CounterThread4(Integer.toString(4),new MyCounter((long)0,(long)0,(long)0));
        CounterThread5 thread5=new CounterThread5(Integer.toString(5),new MyCounter((long)0,(long)0,(long)0));
        long time1 = System.currentTimeMillis();
        for (int i = 0; i <10 ; i++) {
            new Thread(thread1).start();
        }
        thread1.latch.await();
        long time2 = System.currentTimeMillis();

        for (int i = 0; i <10 ; i++) {
            new Thread(thread2).start();
        }
        thread2.latch.await();
        long time3 = System.currentTimeMillis();


        for (int i = 0; i <10 ; i++) {
            new Thread(thread3).start();
        }
        thread3.latch.await();
        long time4 = System.currentTimeMillis();


        for (int i = 0; i <10 ; i++) {
            new Thread(thread4).start();
        }
        thread4.latch.await();
        long time5 = System.currentTimeMillis();

        for (int i = 0; i <10 ; i++) {
            new Thread(thread5).start();
        }
        thread5.latch.await();
        long time6 = System.currentTimeMillis();

        System.out.print("normal cost time="+(time2-time1));
        System.out.println("    normal value="+thread1.myCounter.getCurValue1());

        System.out.print("volatile cost time="+(time3-time2));
        System.out.println("     volatile value="+thread2.myCounter.getCurValue2());

        System.out.print("synchronize cost time="+(time4-time3));
        System.out.println("    synchronize value="+thread3.myCounter.getCurValue3());

        System.out.print("atomicLong cost time="+(time5-time4));
        System.out.println("    atomicLong value="+thread4.myCounter.getCurValue4().get());

        System.out.print("LongAdder cost time="+(time6-time5));
        System.out.println("    LongAdder value="+thread5.myCounter.getCurValue5().sum());

    }
}
