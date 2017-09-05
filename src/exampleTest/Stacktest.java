package exampleTest;

import java.nio.ByteBuffer;

/**
 * Created by thinkpad on 2017/8/20 0020.
 */
public class Stacktest {
    public static void main(String[] args) {
        for(int i=0;i<1024;i++){
            ByteBuffer.allocateDirect(1024*1024);
            System.out.println(i);
//            System.gc();
        }

    }
}
