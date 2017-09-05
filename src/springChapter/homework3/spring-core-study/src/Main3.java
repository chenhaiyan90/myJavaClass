import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main3 {  
  
    public static void main(String[] args) throws InterruptedException {  
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");  
        leader.service.HelloWorldBean helloworld = (leader.service.HelloWorldBean) ctx.getBean("helloBean");  
        helloworld.hello(); 
   
    }  
}  