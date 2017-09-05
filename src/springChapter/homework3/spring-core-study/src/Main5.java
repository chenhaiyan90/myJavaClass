import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import leader.service.MyStoreService;

public class Main5 {  
  
    public static void main(String[] args) throws InterruptedException {  
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");  
    	MyStoreService storService=ctx.getBean(MyStoreService.class);
       System.out.println("MyStoreService  "+storService);

    }  
}  