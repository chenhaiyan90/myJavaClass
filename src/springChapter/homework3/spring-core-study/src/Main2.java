import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {  
  
    public static void main(String[] args) throws InterruptedException {  
        System.out.println(new Date()+ "  begin");  
//      HelloWorld helloworld = new HelloWorld();  
//      helloworld.setName("hfkjdshf");  
          
        ApplicationContext ctx = 
        		   new AnnotationConfigApplicationContext(HelloWorldConfig.class);
        HelloWorldBean helloworld = (HelloWorldBean) ctx.getBean("helloBean");  
        HelloWorldBean helloworld2 = (HelloWorldBean) ctx.getBean("helloBean");
        helloworld.hello();
        helloworld2.hello();

          
    }  
}  