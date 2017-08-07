package springChapter.homework1;

/**
 * Created by thinkpad on 2017/8/7 0007.
 */
public class ConcreteComponent implements Component {

    @Override
    public void doSomething() {
        System.out.println("original function");
    }
}
