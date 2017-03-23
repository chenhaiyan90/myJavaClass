package homework5;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by chenhaiyan on 2017/1/9.
 */
public abstract class AbstractMyCounter {
    private long value;//根据需要进行替换
    public abstract  void incr(Long value1);
    public abstract  void incr(Long value1,Long value2);
    public abstract  void incr(Long value1,Long value2,Long value3);
    public abstract  void incr(Long value1,Long value2,Long value3,Long value4);
    public abstract  void incr(Long value1,Long value2,Long value3,Long value4,Long value5);
    public abstract long getCurValue1();
    public abstract long getCurValue2();
    public abstract long getCurValue3();
    public abstract AtomicLong getCurValue4();
    public abstract LongAdder getCurValue5();
}
