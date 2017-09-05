package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by thinkpad on 2017/8/6 0006.
 */
public class socketDemo {
    public static void main(String[] args) throws IOException {
        URL url =new URL("https://github.com/waylau/spring-framework-4-reference");
        InputStream is = url.openStream();//通过openStream方法获取资源的字节输入流
        InputStreamReader isr =new InputStreamReader(is,"UTF-8");//将字节输入流转换为字符输入流,如果不指定编码，中文可能会出现乱码
        BufferedReader br =new BufferedReader(isr);//为字符输入流添加缓冲，提高读取效率
        String data = br.readLine();//读取数据
        while(data!=null){
            System.out.println(data);//输出数据
            data = br.readLine();
            System.out.println("____aaa___");
            }
        br.close();
        isr.close();
        is.close();
    }
}
