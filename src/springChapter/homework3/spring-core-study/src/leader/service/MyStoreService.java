package leader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyStoreService {
@Autowired
public MyStoreService( Environment env)
{
	System.out.println("MyStoreService created ,use store location "+env.getProperty("system.storefile"));
	System.out.println("mydebug "+env.getProperty("mydebug"));
	System.out.println("temp dir "+env.getProperty("java.io.tmpdir"));
	System.out.println("JAVA_HOME "+env.getProperty("JAVA_HOME"));
	System.out.println("BONJOUR_SDK_HOME "+env.getProperty("BONJOUR_SDK_HOME"));
	
}
}
