import java.util.Map;

/**
 * Created by chenhaiyan on 2016/12/5.
 */
public class Problem2 {
    public static void main(String[] args) {
        int a=-1024;       //  源码 1000 0000 0000 0000 0000 0100 0000 0000
                           //  反码 1111 1111 1111 1111 1111 1011 1111 1111
                           //  补码 1111 1111 1111 1111 1111 1100 0000 0000   java  中用补码表示

        int b=a>>1;//-512  //  源码 1100 0000 0000 0000 0000 0010 0000 0000
                           //  反码 1011 1111 1111 1111 1111 1101 1111 1111
                           //  补码 1011 1111 1111 1111 1111 1110 0000 0000
                           //
        int c=a>>>1;       //  源码 0100 0000 0000 0000 0000 0010 0000 0000
                           //  反码 0011 1111 1111 1111 1111 1101 1111 1111
                           //  补码 0011 1111 1111 1111 1111 1110 0000 0000



        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println("a的二进制为:"+Integer.toBinaryString(a));
        System.out.println("b的二进制为:"+Integer.toBinaryString(b));//0100 0000 0000 0000 0000 0010 0000 0000
        System.out.println("c的二进制为:"+Integer.toBinaryString(c));
        System.out.println((int)(Math.pow(2,30)+Math.pow(2,9)));
    }
}
