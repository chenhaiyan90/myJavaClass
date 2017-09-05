package homework7;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by chenhaiyan on 2016/12/5.
 */
public class SearchFile {
    private static ArrayList<String> filelist = new ArrayList<String>();
    public static void main(String[] args) throws IOException {

//        String filePath = "../";
//        getFiles(filePath);
    }
    static void getFiles(String filePath){
        File root = new File(filePath);
        File[] files = root.listFiles();
        for(File file:files){
            if(file.isDirectory()){
      /*
       * 递归调用
       */
                getFiles(file.getAbsolutePath());
                filelist.add(file.getAbsolutePath());
                System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
            }else{
                System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
            }
        }
    }

    public void excutorTask() throws IOException {
        ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Files.walk(new File("../").toPath(),4)
                .filter(path -> !Files.isDirectory(path)&&path.endsWith(".java")||path.endsWith(".text"))
                .map(fileFullName->new Callable<Pair<Path,Long>>() {
                    @Override
                    public Pair<Path, Long> call() throws Exception {
                        long count=0;
                        count=Files.lines(fileFullName).filter(linecontent->{return linecontent.contains("public");}).count();
                        if(count>0){
                            System.out.println(fileFullName.toString()+"has public "+count);
                            return new Pair<Path, Long>(fileFullName,count);
                        }else return null;
                    }
                })
                .map(c ->executorService.submit(c))
                .collect(Collectors.toList());
    }
}
