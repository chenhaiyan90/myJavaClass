package exampleTest;

/**
 * Created by chenhaiyan on 2016/12/21.
 */
public class testHexString {
    public static void main(String[] args) {

        char hex[]={
                '0','1','2','3','4','5','6','7',
                '8','9','a','b','c','d','e','f'
        };
        byte b=(byte)0xf1;
        System.out.println(0x0f);
        System.out.println("b=0x"+hex[(b>>4)&0x0f]+hex[b&0x0f]);
    }
}
