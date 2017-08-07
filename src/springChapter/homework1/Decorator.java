package springChapter.homework1;

public class Decorator implements Component {
    private Component component;
    public Decorator(Component component) {
        this.component = component;
    }
    @Override
    public void doSomething() {
        component.doSomething();
        this.doAnotherThing();
    }
    private void doAnotherThing() {
        System.out.println("more function");
    }
}
