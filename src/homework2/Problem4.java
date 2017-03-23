package homework2;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by chenhaiyan on 2016/12/19.
 */
public class Problem4 {
    public static final int NUMBER=100;
    public static void main(String[] args) throws IOException {
        generatorFile();
        groupFile();
    }
    private static void generatorFile() throws IOException {
        File file = new File("C:\\Users\\00013810\\Desktop\\myfile4.dat");
        if(file.exists()){
            file.delete();
        }
        RandomAccessFile rf=new RandomAccessFile("C:\\Users\\00013810\\Desktop\\myfile4.dat","rw");
        FileChannel filech=rf.getChannel();
        MappedByteBuffer buffer=filech.map(FileChannel.MapMode.READ_WRITE,0,NUMBER*15);
        long time1=System.currentTimeMillis();
        for (int i = 0; i <NUMBER; i++) {
            Salary salary=new Salary(generatorName(5).getBytes(StandardCharsets.UTF_8),generatorBaseSalary(),generatorBonus());
                buffer.put(salary.name);
                buffer.put(intToByteArray(salary.baseSalary));
                buffer.put(intToByteArray(salary.bonus));
                buffer.put("\r\n".getBytes());
        }
        Long time2=System.currentTimeMillis();
        System.out.println("cost time"+(time2-time1));
    }
     private static void groupFile() throws IOException {
         RandomAccessFile rf=new RandomAccessFile("C:\\Users\\00013810\\Desktop\\myfile4.dat","r");
         FileChannel filech=rf.getChannel();
         MappedByteBuffer buffer=filech.map(FileChannel.MapMode.READ_ONLY,0,filech.size());
//         Map<String,List<Salary>> mapSalarys=null;
         List<Salary> salaryList= new ArrayList<>();
         for (int i = 0; i <NUMBER ; i++) {
                 byte[] begin5=new byte[5];
                 byte[] begin6=new byte[4];
                 byte[] begin7=new byte[4];
                 buffer.get(begin5,0,5);
                 buffer.get(begin6,0,4);
                 buffer.get(begin7,0,4);
                 buffer.get();
                 buffer.get();
                 Salary salary=new Salary(begin5,bytesToInt(begin6),bytesToInt(begin7));
                salaryList.add(salary);
         }
         long time1=System.currentTimeMillis();
         salaryList.stream()
                 .filter(o->(o.getBaseSalary()+o.getBonus())>10)
                 .collect(Collectors.groupingBy(
                         o->o.getName(),
                         Collector.of(
                                 ()->new Long[2],
                                 (a,b)->{a[0]+=Long.valueOf(b.getBaseSalary()+b.getBonus());a[1]+=1L;},
                                 (a,b)->{a[0]+=b[0];a[1]+=b[1];return a;}
                         )))
                 .entrySet()
                 .stream().sorted((o1,o2)->Integer.compare(o2.getValue()[0].intValue(),o1.getValue()[0].intValue()))
                 .limit(10)
                 .forEachOrdered(o->System.out.println(o.getKey()+":"+"sumSalsry:"+o.getValue()[0]+"total:"+o.getValue()[1]));
         System.out.println("stream cost time:"+(System.currentTimeMillis()-time1));
     }
    private static Integer generatorBaseSalary(){
        Random random=new Random();
        return random.nextInt(10);
    }
    private static Integer generatorBonus(){
        Random random=new Random();
        return random.nextInt(5);
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
    public static int bytesToInt(byte[] src) throws IOException {
        ByteArrayInputStream buf = new ByteArrayInputStream(src);
        DataInputStream dis= new DataInputStream (buf);
        return dis.readInt();
    }
    public static byte[] intToByteArray(int a) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos= new DataOutputStream(buf);
        dos.writeInt(a);
        byte[] b = buf.toByteArray();
        dos.close();
        buf.close();
        return b;
    }
}
