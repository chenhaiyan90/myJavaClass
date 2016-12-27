package exampleTest;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by chenhaiyan on 2016/12/22.
 */
public class TestRandomAccessFile {
    public static void main(String[] args) {
        String pathname = "Recommended system.txt";
        // 创建文件实例
        File file = new File(pathname);
        try {
            // 判断文件是否存在
            if(!file.exists()){
                file.createNewFile();
            }//if

            // 读写方式打开文件
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            // 写值
            for(int i = 0;i < 5;++i){
                randomAccessFile.writeInt(i);
            }//for
            randomAccessFile.write("陈海燕哈哈哈哈".getBytes());

            // 将文件指针移到第二个Int值后
            randomAccessFile.seek(2*4);
            // 覆盖第三个Int值
            randomAccessFile.writeInt(6);

            // 文件指针指向文件开头
            randomAccessFile.seek(0);
            // 输出
            for (int i = 0;i < 5;++i) {
                System.out.print(randomAccessFile.readInt()+" ");
            }//for
            randomAccessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
