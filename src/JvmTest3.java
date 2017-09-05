/**
 * Created by thinkpad on 2017/3/27 0027.
 */
public class JvmTest3 {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);
    }
}
