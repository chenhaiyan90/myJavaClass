package homework2;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;


/**
 * Created by chenhaiyan on 2016/12/19.
 */
public class Problem2 {
    public static void main(String[] args) throws IOException {
        int number=10240;
        RandomAccessFile rf=new RandomAccessFile("myfile.dat","rw");
        byte[] bigEnDianBytes=intToByte4(number);
        System.out.println("bigEndianBytes is"+Arrays.toString(bigEnDianBytes));
        byte[] littleEndianBytes=new byte[bigEnDianBytes.length];
        for (int i = 0; i <bigEnDianBytes.length ; i++) {
            littleEndianBytes[bigEnDianBytes.length-i-1]=bigEnDianBytes[i];
        }
        System.out.println("littleEndianBytes is"+Arrays.toString(littleEndianBytes));

        try {
            rf.write(bigEnDianBytes);
            rf.write("\r\n".getBytes());
            rf.write(littleEndianBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            rf.close();
        }
    }
    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }
}
