package exampleTest;

import java.io.*;

/**
 * Created by chenhaiyan on 2016/12/22.
 */
public class TestDataInputSteam {
    public static void main(String[] args) {
        // 使用DataInputStream,DataOutputStream写入文件且从文件中读取数据。
        try {
            // Data Stream写到输入流中
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                    "datasteam.txt"));
            dos.writeBytes("世界"); //按2字节写入，都是写入的低位
            dos.writeChars("世界"); // 按照Unicode写入
            // 按照UTF-8写入(UTF8变长，开头2字节是由writeUTF函数写入的长度信息，方便readUTF函数读取)
            dos.writeUTF("世界");
            dos.flush();
            dos.close();
            // Data Stream 读取
            DataInputStream dis = new DataInputStream(new FileInputStream(
                    "datasteam.txt"));
            // 读取字节
            byte[] b = new byte[2];
            dis.read(b);
            System.out.println(new String(b, 0, 2));

            // 读取字符
            char[] c = new char[2];
            for (int i = 0; i < 2; i++) {
                c[i] = dis.readChar();
            }
            System.out.println(new String(c, 0, 2));

            // 读取UTF
            System.out.println(dis.readUTF());

            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
