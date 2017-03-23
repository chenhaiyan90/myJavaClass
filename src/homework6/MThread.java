package homework6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chenhaiyan on 2017/1/16.
 */
class MThread extends Thread
{

    public MThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            TestOnly.lock.lock();
            try {
                while (TestOnly.datas.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " into wait ,because empty ");
                    try {
                        TestOnly.empty.await();
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + " wait finished ");
                }
                if (TestOnly.datas.isEmpty()) {
                    System.out.println("impossible empty !! " + Thread.currentThread().getName());
                    System.exit(-1);
                }
                TestOnly.datas.forEach(s -> System.out.println(s));
                TestOnly.datas.clear();
            } finally {
                TestOnly.full.signal();
                TestOnly.lock.unlock();
            }
        }
    }
}