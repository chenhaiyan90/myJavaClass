package homework7;

import sun.dc.path.PathError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by chenhaiyan on 2017/2/7.
 */
public class SearchPath {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService= Executors.newFixedThreadPool(1);

        //get path
        Path path= Paths.get(System.getProperty("user.dir"));
        List<Path> results= Files.walk(path,5)
                .filter(p->{
                    String ext=p.toFile().getName();
                    return !Files.isDirectory(p)&&ext.endsWith(".java")||ext.endsWith(".txt");
                })
                .collect(Collectors.toList());
        results.stream().forEach(path1 -> System.out.println(path1.toString()));
    }

    private void verifyParam(File file, String keyword) {
        //对参数进行校验证
        if(file == null ){
            throw new NullPointerException("the file is null");
        }
        if(keyword == null || keyword.trim().equals("")){
            throw new NullPointerException("the keyword is null or \"\" ");
        }
        if(!file.exists()) {
            throw new RuntimeException("the file is not exists");
        }
        //非目录
        if(file.isDirectory()){
            throw new RuntimeException("the file is a directory,not a file");
        }
        //可读取
        if(!file.canRead()) {
            throw new RuntimeException("the file can't read");
        }
    }

}
class SearchResult implements Callable{

    @Override
    public Object call() throws Exception {
        return null;
    }
}
