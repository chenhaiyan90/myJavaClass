import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import leader.service.MySpringEvtSender;

public class Main13 {  
  
    public static void main(String[] args) throws InterruptedException {  
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");  
    	MySpringEvtSender evtSender=ctx.getBean(MySpringEvtSender.class);
    	evtSender.createDemoEvent();
       
      Thread.sleep(5000);
      
   
    }  
}  