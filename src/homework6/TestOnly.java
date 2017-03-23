package homework6;

/**
 * Created by chenhaiyan on 2017/1/16.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOnly {
    final static Lock lock=new ReentrantLock();
    final static Condition full=lock.newCondition();
    final static Condition empty=lock.newCondition();

    static ArrayList<String> datas=new  ArrayList<String>();

    public static void main(String[] args) throws InterruptedException
    {
        List<Thread> threads= IntStream.range(1, 10)
                .mapToObj(i->{if(i%2==0) {return new MThread("consumer "+i);}
                else return new NThread("producer "+i);})
                .filter(t->{t.start();return true;})
                .collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        });

    }
}
