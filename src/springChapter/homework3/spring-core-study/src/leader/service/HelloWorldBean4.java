package leader.service;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldBean4 {

public HelloWorldBean4()
	{
		System.out.println(new Date()+" created "+ this);
	}

	@PostConstruct
	public void init()
	{
		System.out.println("inited ");
		// open jdbc pool ,or thread pool 
		
	}
	public void hello() {
		System.out.println(new Date()+" hellow "+ " @ "+ this);
		
	}
//	@Autowired
//public void setAnotherHelloBean(HelloWorldBean theBean)
//{
//	
//}
	@Autowired
public void setHelloBean(HelloWorldBean anotherHelloBean)
{
	
}
	@PreDestroy
	public void close()
	{
		System.out.println("closed ");
		// close jdbc pool ,or thread pool  ,release resources
	}
}
