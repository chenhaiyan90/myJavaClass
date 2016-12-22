package homework2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by chenhaiyan on 2016/12/19.
 */
public class Problem4 {
    public static final int NUMBER=10000;
    public static void main(String[] args) throws FileNotFoundException {
        generatorFile();
        groupFile();
    }
    private static void generatorFile() throws FileNotFoundException {
        File file = new File("C:\\Users\\00013810\\Desktop\\myfile4.dat");
        if(file.exists()){
            file.delete();
        }
        RandomAccessFile rf=new RandomAccessFile("C:\\Users\\00013810\\Desktop\\myfile4.dat","rw");
        FileChannel filech=rf.getChannel();
        long time1=System.currentTimeMillis();
        for (int i = 0; i <NUMBER ; i++) {
            Salary salary=new Salary(generatorName(5).getBytes(StandardCharsets.UTF_8),generatorBaseSalary(),generatorBonus());
            try {
                MappedByteBuffer buffer=filech.map(FileChannel.MapMode.READ_WRITE,i*9,9);
                buffer.put(salary.name);
                buffer.put(Integer.valueOf(salary.baseSalary).byteValue());
                buffer.put(Integer.valueOf(salary.bonus).byteValue());
                buffer.put("\r\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Long time2=System.currentTimeMillis();
        System.out.println("cost time"+(time2-time1));
    }
     private static void groupFile() throws FileNotFoundException {
         RandomAccessFile rf=new RandomAccessFile("C:\\Users\\00013810\\Desktop\\myfile4.dat","r");
         FileChannel filech=rf.getChannel();
         Map<String,List<Salary>> mapSalarys=null;
         for (int i = 0; i <NUMBER ; i++) {
             try {
                 byte[] begin2=new byte[2];
                 byte[] begin5=new byte[5];
                 byte[] begin6=new byte[1];
                 byte[] begin7=new byte[1];
                 MappedByteBuffer buffer=filech.map(FileChannel.MapMode.READ_ONLY,i*9,9);
                 buffer.get(begin2,0,2);
                 buffer.clear();
                 buffer.get(begin5,0,4);
                 buffer.get(begin6,0,1);
                 buffer.get(begin7,0,1);
                 Salary salary=new Salary(begin5,bytesToInt(begin6,0),bytesToInt(begin7,0));
                 String str=new String(begin2,StandardCharsets.UTF_8);
                 if(mapSalarys.containsKey(str)){
                     mapSalarys.get(str).add(salary);
                 }else {
                     mapSalarys.put(str,new ArrayList<>());
                     mapSalarys.get(str).add(salary);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }
    private static Integer generatorBaseSalary(){
        Random random=new Random();
        return random.nextInt(95)+5;
    }
    private static Integer generatorBonus(){
        Random random=new Random();
        return random.nextInt(10);
    }
    private static String generatorName(int length){
        String base = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        return value;
    }
}
