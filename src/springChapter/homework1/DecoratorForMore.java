package springChapter.homework1;

public class DecoratorForMore extends Decorator{
    public DecoratorForMore(Component component) {
        super(component);
    }
    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }
    private void doAnotherThing() {
        System.out.println("more more function");
    }
}