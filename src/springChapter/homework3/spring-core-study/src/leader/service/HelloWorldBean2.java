package leader.service;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldBean2 {

	@Value("${system.appname}")
	private String myName;
	public HelloWorldBean2()
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
