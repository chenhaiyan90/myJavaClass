import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhaiyan on 2017/1/19.
 */
public class TestCPUProcessors {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        Map<String,Object> map= new HashMap<>();
        System.out.println(map.get("ddd"));
    }
}
