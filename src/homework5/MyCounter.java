package homework5;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by chenhaiyan on 2017/1/9.
 */
public class MyCounter extends AbstractMyCounter {

    private Long normalVarValue;
    private volatile Long volValue;
    private long syncValue;
    static AtomicLong atomicLong=new AtomicLong();
    static LongAdder  longAdder=new LongAdder();

    public MyCounter(Long normalVarValue, Long volValue, long syncValue) {
        this.normalVarValue = normalVarValue;
        this.volValue = volValue;
        this.syncValue = syncValue;
    }

    @Override
    public void incr(Long normalVarValue) {
        this.normalVarValue+=normalVarValue;
    }

    @Override
    public void incr(Long value1, Long value2) {

        this.volValue+=value2;
    }

    @Override
    public void incr(Long value1, Long value2, Long value3) {
        this.syncValue+=value3;
    }

    @Override
    public void incr(Long value1, Long value2, Long value3, Long value4) {
        this.atomicLong.incrementAndGet();
    }

    @Override
    public void incr(Long value1, Long value2, Long value3, Long value4, Long value5) {
        this.longAdder.increment();
    }

    @Override
    public long getCurValue1() {
        return normalVarValue;
    }

    @Override
    public long getCurValue2() {
        return volValue;
    }

    @Override
    public long getCurValue3() {
        return syncValue;
    }

    @Override
    public AtomicLong getCurValue4() {
        return atomicLong;
    }

    @Override
    public LongAdder getCurValue5() {
        return longAdder;
    }
}
