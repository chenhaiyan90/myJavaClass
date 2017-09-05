import org.springframework.context.support.GenericXmlApplicationContext;

import leader.service.HelloWorldBean2;

public class Main7 {  
  
    public static void main(String[] args) throws InterruptedException { 
    	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("applicationContext2.xml");
    	HelloWorldBean2 hello=ctx.getBean(HelloWorldBean2.class);
    	hello.hello();
       ctx.close();

    }  
}  