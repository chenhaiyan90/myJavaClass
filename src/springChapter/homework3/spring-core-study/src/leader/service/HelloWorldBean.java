package leader.service;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("helloBean")
public class HelloWorldBean {

	private String myName;
	public HelloWorldBean()
	{
		System.out.println(new Date()+" created "+ this);
	}

	public void hello() {
		System.out.println(new Date()+" hellow "+myName+ " @ "+ this);
		
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}
	
}
