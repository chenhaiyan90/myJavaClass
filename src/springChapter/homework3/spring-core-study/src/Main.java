import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {  
  
    public static void main(String[] args) throws InterruptedException {  
        System.out.println(new Date()+ "  begin");  
//      HelloWorld helloworld = new HelloWorld();  
//      helloworld.setName("hfkjdshf");  
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader.getResource("").getPath());

//        System.out.println(this.getClass().getResource("").getPath());
//        System.out.println(this.getClass().getResource("/").getPath());

        System.out.println(System.getProperty("user.dir"));
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       
        Thread.sleep(5000);
        HelloWorldBean helloworld = (HelloWorldBean) ctx.getBean("helloBean");  
        HelloWorldBean helloworld2 = (HelloWorldBean) ctx.getBean("helloBean");
        System.out.println(new Date()+ " get bean "+helloworld);
        System.out.println(new Date()+ " get bean2 "+helloworld2);
        Thread.currentThread();
		Thread.sleep(5000);
        helloworld.hello(); 
        helloworld2.hello();

          
    }  
}  