package leader.reflect;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

public class ClassUtil {
	public static File[] getPackageContent(String packageName) throws IOException{
	    ArrayList<File> list = new ArrayList<File>();
	    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
	                            .getResources(packageName);
	    while (urls.hasMoreElements()) {
	        URL url = urls.nextElement();
	        File dir = new File(url.getFile());
	        for (File f : dir.listFiles()) {
	            list.add(f);
	        }
	    }
	    return list.toArray(new File[]{});
	}
}
