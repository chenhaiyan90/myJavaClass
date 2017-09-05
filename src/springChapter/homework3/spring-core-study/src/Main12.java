import org.springframework.context.support.GenericXmlApplicationContext;

import leader.service.AppContextUtil;
import leader.service.CompletedService2;

public class Main12 {  
  
    public static void main(String[] args) throws InterruptedException { 
    	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("applicationContext4.xml");
//    	CompletedService myServc=ctx.getBean(CompletedService.class);
//    	System.out.println("map class "+myServc.getAllDaos().getClass());
//    	myServc.getAllDaos().forEach((key,value)->{System.out.println(key+" "+value);});
    	
    	 System.out.println(AppContextUtil.getBean(CompletedService2.class));
    	CompletedService2 myServc2=ctx.getBean(CompletedService2.class);
    	System.out.println(" class "+myServc2.getAllDaos().getClass());
    	myServc2.getAllDaos().forEach((a)->{System.out.println(a);});
    	ctx.registerShutdownHook();
       

    }  
}  