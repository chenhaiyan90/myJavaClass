import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import leader.service.MyOrderService;
import leader.service.MySmsService;

public class Main4 {  
  
    public static void main(String[] args) throws InterruptedException {  
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
        System.out.println(ctx.getApplicationName());
        MyOrderService orderSvc=ctx.getBean(MyOrderService.class);
       System.out.println("local storage of MyOrderService "+orderSvc.getLocalStorage());
       
       MySmsService smsSvc=ctx.getBean(MySmsService.class);
       System.out.println("local storage of MySmsService "+smsSvc.getLocalStorage());
   
    }  
}  