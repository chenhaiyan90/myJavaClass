package springChapter.homework1;

/**
 * Created by thinkpad on 2017/8/7 0007.
 */
public class ClientForDecorator {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        System.out.println("-----ConcreteComponent function:------");
        component.doSomething();
        System.out.println("-----end------");
        System.out.println("");
        Component component1 = new Decorator(component);
        System.out.println("-----Decorator function:------");
        component1.doSomething();
        System.out.println("-----end------");
        System.out.println("");

        System.out.println("-----DecoratorForMore function:------");
        Component component2 = new DecoratorForMore(component1);
        component2.doSomething();
        System.out.println("-----end------");
    }
}
