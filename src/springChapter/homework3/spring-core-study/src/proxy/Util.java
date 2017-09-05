package proxy;

import java.io.FileOutputStream;
import java.io.IOException;

public class Util {
	public static void genProxy2file(Class theClsstoProxy) {  
        byte[] classFile =sun.misc.ProxyGenerator.generateProxyClass("$Proxy0", theClsstoProxy.getInterfaces());  
          
        FileOutputStream out = null;  
          
        try {  
            out = new FileOutputStream("myproxy.class");  
            out.write(classFile);  
            out.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
