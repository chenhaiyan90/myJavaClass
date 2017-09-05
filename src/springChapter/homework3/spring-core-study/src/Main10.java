import org.springframework.context.support.GenericXmlApplicationContext;

import leader.service.HelloWorldBean4;

public class Main10 {  
  
    public static void main(String[] args) throws InterruptedException { 
    	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("applicationContext2.xml");
    	HelloWorldBean4 hello=ctx.getBean(HelloWorldBean4.class);
    	hello.hello();
    	
    	ctx.registerShutdownHook();
       

    }  
}  