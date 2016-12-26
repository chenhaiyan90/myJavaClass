package exampleTest;

import java.io.*;

/**
 * Created by chenhaiyan on 2016/12/22.
 */
public class TestDataInputStream {
    public static void main(String[] args) throws IOException {
        DataOutputStream dataOutputStream=null;
        try {
            dataOutputStream=new DataOutputStream(new FileOutputStream("testDataOutPutStream.txt"));
            dataOutputStream.writeChars("chen");
            dataOutputStream.writeUTF("陈海燕");
            dataOutputStream.write("\r\n".getBytes());
            dataOutputStream.writeUTF("陈海燕 is really  great");
            dataOutputStream.write("\r\n".getBytes());
            dataOutputStream.writeUTF("haiyanchen  is really  great man");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DataInputStream dataInputStream=new DataInputStream(new FileInputStream("testDataOutPutStream.txt"));
        System.out.println(dataInputStream.readChar());
        System.out.println(dataInputStream.readChar());
        System.out.println(dataInputStream.readChar());
        System.out.println(dataInputStream.readChar());
        System.out.println(dataInputStream.readUTF());
        System.out.println(dataInputStream.readUTF());


    }
}
