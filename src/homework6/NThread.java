package homework6;

import java.util.stream.IntStream;

/**
 * Created by chenhaiyan on 2017/1/16.
 */
class NThread extends Thread
{
    public NThread(String string) {
        this.setName(string);
    }

    public void run()
    {
        while(true)
        {
            TestOnly.lock.lock();
            try {
                while (TestOnly.datas.size() > 1) {
                    System.out.println(Thread.currentThread().getName() + " into wait,because full ");
                    try {
                        TestOnly.full.await();
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + "wait finished ");
                }
                if (TestOnly.datas.size() > 1) {
                    System.out.println("impossible full !! " + Thread.currentThread().getName());
                    System.exit(-1);
                }
                IntStream.range(0, 1).forEach(i -> TestOnly.datas.add(i + " data"));
            } finally {
                TestOnly.empty.signal();
                TestOnly.lock.unlock();
            }

        }
    }
}
