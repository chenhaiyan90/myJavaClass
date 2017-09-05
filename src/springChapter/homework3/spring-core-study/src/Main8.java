import org.springframework.context.support.GenericXmlApplicationContext;

import leader.service.HelloWorldBean3;

public class Main8 {  
  
    public static void main(String[] args) throws InterruptedException { 
    	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("applicationContext2.xml");
    	HelloWorldBean3 hello=ctx.getBean(HelloWorldBean3.class);
    	hello.hello();
    	hello=ctx.getBean(HelloWorldBean3.class);
       //ctx.close();
       //Thread.sleep(5000);
    	ctx.registerShutdownHook();
       

    }  
}  