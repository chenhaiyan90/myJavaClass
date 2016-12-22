package homework2;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by chenhaiyan on 2016/12/19.
 */
public class Problem1 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str="中国";
        byte[] bytesUtf_8=str.getBytes(StandardCharsets.UTF_8);
        byte[] bytesIos_8859_1=str.getBytes(StandardCharsets.ISO_8859_1);
        byte[] bytesGbk=str.getBytes("GBK");
        System.out.println(Arrays.toString(bytesUtf_8));//[-28, -72, -83, -27, -101, -67]  6个字节
        System.out.println(Arrays.toString(bytesIos_8859_1));//[63, 63]  2个字节
        System.out.println(Arrays.toString(bytesGbk));//[-42, -48, -71, -6]  4字节
        String str3=new String(bytesUtf_8,"GBK");
        System.out.println(str3);//编码方式不一样 不能还原
        /*
        GBK的文字编码是用双字节来表示的，即不论中、英文字符均使用双字节来表示，为了区分中文，将其最高位都设定成1
        GBK、GB2312等与UTF8之间都必须通过Unicode编码才能相互转换
        UTF_8 是用以解决国际上字符的一种多字节编码，它对英文使用8位（即一个字节），中文使用24为（三个字节）来编码。
        UTF-8包含全世界所有国家需要用到的字符，是国际编码，通用性强。
        UTF-8编码的文字可以在各国支持UTF8字符集的浏览器上显示。如果是UTF8编码，则在外国人的英文IE上也能显示中文
        */
        String str1=new String(new String(bytesUtf_8,"GBK").getBytes("GBK"),StandardCharsets.UTF_8);//utf8 编码的byte数组可以通过GBK转换成字符串，然后转成GBK编码的byte数组，
        String str2=new String(new String(bytesGbk,StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8),"GBK");//GBK 编码的byte数组不行，
        /*b[]字节数组里面存的是gbk编码，
        如果用utf-8到unicode的转换算法。
        这个字节数组里面的某些位用这种算法无法解析，
        于是数据就出现了错误，数据错误之后，想转换回来当然不行了(所谓的兼容性不好)。*/
        System.out.println(str1);
        System.out.println(str2);
    }
}
